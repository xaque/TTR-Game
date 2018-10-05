package cs340.game.client;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.Presenters.GameListPresenter;
import cs340.game.client.Presenters.GameLobbyPresenter;
import cs340.game.client.Presenters.MainActivityPresenter;
import cs340.game.shared.*;
import cs340.game.shared.models.*;

/**
 * Handles communication with the ClientModelRoot object. Meant to limit dependencies between the
 * Presenters and the Models.
 */
public class AppLayerFacade{

    // Start Singleton
    private static AppLayerFacade instance;

    private AppLayerFacade(){}

    public static AppLayerFacade getInstance() {

        if(instance == null){

            instance = new AppLayerFacade();
        }

        return instance;
    }
    // End Singleton

    private ServerProxy proxy = new ServerProxy();
    private ClientModelRoot clientModelRoot = ClientModelRoot.getInstance();

    /**
     * Logs the player in. Communicates with the client-side models and the ServerProxy in order
     * to log a user into the game. Notifies the necessary Presenter of the success of the request.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param username the username of the user to be logged in
     * @param password the password of the user to be logged in
     */
    public void Login(MainActivityPresenter presenter, String username, String password){

        if(clientModelRoot.getCurrentUser() != null){
            presenter.onError("Already logged in!");
            return;
        }

        User user = new User(username, password);
        Results results = proxy.Login(user);

        if(results.isSuccess()) {
            String authToken = "";//results.getAuthToken();
            user.setAuthToken(authToken);
            clientModelRoot.setCurrentUser(user);
            clientModelRoot.setUserState(UserState.IN_LOBBY);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        // Start Poller
        clientModelRoot.startPoller();

        presenter.onLoginResponse(results.isSuccess());
    }

    /**
     * Registers a user and then logs them in. Communicates with the client-side models and the
     * ServerProxy in order to register a user and then log them into the game. Notifies the
     * necessary Presenter of the success of the request.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param username the username of the user to be registered and then logged in
     * @param password the password of the user to be registered and then logged in
     */
    public void Register(MainActivityPresenter presenter, String username, String password){

        if(clientModelRoot.getCurrentUser() != null){
            presenter.onError("Already logged in!");
            return;
        }

        User user = new User(username, password);
        Results results = proxy.Register(user);

        if(results.isSuccess()) {
            String authToken = "";//results.getAuthToken();
            user.setAuthToken(authToken);
            clientModelRoot.setCurrentUser(user);
            clientModelRoot.setUserState(UserState.IN_LOBBY);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        // Start Poller
        clientModelRoot.startPoller();

        presenter.onRegisterResponse(results.isSuccess());
    }

    /**
     * Logs the player out. Part of this process is setting the current user to null and stopping
     * the Poller so it does not continue looking for updates when there is nothing to update.
     * @see Poller
     */
    public void Logout(){

        clientModelRoot.setCurrentUser(null);
        clientModelRoot.setUserState(UserState.LOGGED_OUT);
        // Stop Poller
        clientModelRoot.stopPoller();
    }

    /**
     * Create a new game and add the person that created it to that game. This method handles
     * communicating with the client models and with the ServerProxy in order to successfully
     * create a new game.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param gameName the name of the game the will be created
     */
    public void CreateGame(GameListPresenter presenter, String gameName){

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            presenter.onError("You must be logged in to create a game!");
            return;
        }

        GameList games = clientModelRoot.getGames();
        if(games.gameExists(gameName)){
            presenter.onError("A game with this name already exists!");
            return;
        }

        Results results = proxy.CreateGame(gameName, currentUser.getAuthToken());

        if(results.isSuccess()) {
            Game game = new Game(gameName, currentUser.getUsername());
            clientModelRoot.addGame(game);
            clientModelRoot.setCurrentGame(game);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        presenter.onCreateGameResponse(results.isSuccess());
    }

    /**
     * Add the current user to a game. This method handles communicating with the client models
     * and with the ServerProxy in order to successfully add a user to a game.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param gameName the name of the game to which the user will be added
     */
    public void JoinGame(GameListPresenter presenter, String gameName){

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            presenter.onError("You must be logged in to create a game!");
            return;
        }

        Game game = clientModelRoot.getGame(gameName);
        if(game.playerExistsInGame(currentUser.getUsername())){
            presenter.onError("You are already in this game!");
            return;
        }

        if(game.isGameFull()){
            presenter.onError("This game is already full!");
            return;
        }

        Results results = proxy.JoinGame(gameName, currentUser.getAuthToken());

        if(results.isSuccess()){
            clientModelRoot.addPlayerToGame(currentUser.getUsername(), gameName);
            clientModelRoot.setCurrentGame(game);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        presenter.onCreateGameResponse(results.isSuccess());
    }

    /**
     * Starts a game if it has the appropriate number of players in it. This method handles
     * communicating with the client models and with the ServerProxy in order to successfully start
     * a game.
     * @param presenter the Presenter that will be notified of the success of this method
     * @param gameName the name of the game that will be started
     */
    public void StartGame(GameListPresenter presenter, String gameName){

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            presenter.onError("You must be logged in to start a game!");
            return;
        }

        Game game = clientModelRoot.getGame(gameName);
        if(!game.playerExistsInGame(currentUser.getUsername())){
            presenter.onError("You cannot start this game if you are not in it!");
            return;
        }

        if(game.hasEnoughPlayersToStart()){
            presenter.onError("This game does not have enough players!");
            return;
        }

        Results results = proxy.StartGame(gameName, currentUser.getAuthToken());

        if(results.isSuccess()){
            clientModelRoot.startGame(gameName);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }
    }

    public void addObserver(Observer o){
        clientModelRoot.addObserver(o);
    }

    public void deleteObserver(Observer o){
        clientModelRoot.deleteObserver(o);
    }

    public Game getCurrentGame(){
        return clientModelRoot.getCurrentGame();
    }

    public Game getGame(String gameName){
        return clientModelRoot.getGame(gameName);
    }

    public GameList getAllGames(){
        return clientModelRoot.getGames();
    }
}