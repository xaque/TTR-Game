package cs340.game.client;

import java.util.Observer;

import cs340.game.client.Presenters.GameListPresenter;
import cs340.game.client.Presenters.GameLobbyPresenter;
import cs340.game.client.Presenters.MainActivityPresenter;
import cs340.game.shared.CommonData;
import cs340.game.shared.data.LobbyPollerData;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameList;
import cs340.game.shared.models.User;
import cs340.game.shared.results.LobbyPollerResults;
import cs340.game.shared.results.LobbyResults;
import cs340.game.shared.results.LoginResults;

/**
 * Handles communication with the ClientModelRoot object. Meant to limit dependencies between the
 * Presenters and the Models.
 */
public class AppLayerFacade {

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

    /**
     * A reference to the ServerProxy instance, a class that is a proxy for communication to the server.
     */
    private ServerProxy proxy = new ServerProxy();
    /**
     * A reference to the ClientModelRoot instance, a singleton class that holds all model data for the client.
     */
    private ClientModelRoot clientModelRoot = ClientModelRoot.getInstance();

    // LOBBY
    /**
     * Logs the player in. Communicates with the client-side models and the ServerProxy in order
     * to log a user into the game. Notifies the necessary Presenter of the success of the request.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param username the username of the user to be logged in
     * @param password the password of the user to be logged in
     * @pre presenter != null
     * @pre username != null
     * @pre password != null
     * @pre Current User = null
     * @post logs in the newly created user
     * @post starts the poller
     * @post the return String will be null if there was not an error encountered on the server
     */
    public String Login(MainActivityPresenter presenter, String username, String password){

        if(clientModelRoot.getCurrentUser() != null){
            //presenter.onError("Already logged in!");
            return "Already logged in!";
        }

        if(username.isEmpty() || password.isEmpty()){
            return "Must include username and password";
        }

        User user = new User(username, password);
        LoginResults results = (LoginResults) proxy.Login(user);

        if(results.isSuccess()) {
            String authToken = results.getAuthToken();
            user.setAuthToken(authToken);
            clientModelRoot.setCurrentUser(user);
            clientModelRoot.setUserState(UserState.IN_LOBBY);
        }else{
            //presenter.onError(results.getErrorInfo());
            System.out.println(results.getErrorInfo());
            return results.getErrorInfo();
        }

        // Start Poller
        clientModelRoot.startPoller(presenter);

        presenter.onLoginResponse(results.isSuccess());
        return null;
    }

    /**
     * Registers a user and then logs them in. Communicates with the client-side models and the
     * ServerProxy in order to register a user and then log them into the game. Notifies the
     * necessary Presenter of the success of the request.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param username the username of the user to be registered and then logged in
     * @param password the password of the user to be registered and then logged in
     * @pre presenter != null
     * @pre username != null
     * @pre password != null
     * @pre Current User = null
     * @post create a new user with the username and password passed in
     * @post logs in the newly created user
     * @post starts the poller
     * @post the return String will be null if there was not an error encountered on the server
     */
    public String Register(MainActivityPresenter presenter, String username, String password){

        if(clientModelRoot.getCurrentUser() != null){
            //presenter.onError("Already logged in!");
            return "Already logged in!";
        }

        if(username.isEmpty() || password.isEmpty()){
            return "Must include username and password";
        }

        User user = new User(username, password);
        LoginResults results = (LoginResults) proxy.Register(user);

        if(results.isSuccess()) {
            String authToken = results.getAuthToken();
            user.setAuthToken(authToken);
            clientModelRoot.setCurrentUser(user);
            clientModelRoot.setUserState(UserState.IN_LOBBY);
        }else{
            //presenter.onError(results.getErrorInfo());
            System.out.println(results.getErrorInfo());
            return results.getErrorInfo();
        }

        // Start Poller
        clientModelRoot.startPoller(presenter);

        presenter.onRegisterResponse(results.isSuccess());
        return null;
    }

    /**
     * Logs the player out. Part of this process is setting the current user to null and stopping
     * the Poller so it does not continue looking for updates when there is nothing to update.
     * @see Poller
     * @post the Current User = null
     * @post the poller is stopped
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
     * @pre the Current User is not null
     * @pre presenter != null
     * @pre a current game does not have the name passed in
     * @pre gameName != null
     * @post a game with the name passed in is created
     * @post the Current User is in the game created
     * @post the return String will be null if there was not an error encountered on the server
     */
    public String CreateGame(GameListPresenter presenter, String gameName){

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            System.out.println("You must be logged in to create a game!");
            //presenter.onError("You must be logged in to create a game!");
            return "You must be logged in to create a game!";
        }

        GameList games = clientModelRoot.getGames();
        if(games.gameExists(gameName)){
            System.out.println("A game with this name already exists!");
            //presenter.onError("A game with this name already exists!");
            return "A game with this name already exists!";
        }

        if(gameName.isEmpty()){
            return "Must include a game name";
        }

        LobbyResults results = (LobbyResults) proxy.CreateGame(gameName, currentUser.getUsername());

        if(results.isSuccess()) {
            Game game = new Game(gameName, currentUser.getUsername());
            clientModelRoot.addGame(game);
            clientModelRoot.setCurrentGame(game);
        }else{
            System.out.println(results.getErrorInfo());
            //presenter.onError(results.getErrorInfo());
            return results.getErrorInfo();
        }

        System.out.println("onCreateGameResponse");
        presenter.onCreateGameResponse(results.isSuccess());
        return null;
    }

    /**
     * Add the current user to a game. This method handles communicating with the client models
     * and with the ServerProxy in order to successfully add a user to a game.
     * @see ServerProxy
     * @param presenter the Presenter that will be notified of the success of this method
     * @param gameName the name of the game to which the user will be added
     * @pre the Current User is not null
     * @pre presenter != null
     * @pre a game with the name passed in must exist
     * @pre the Current User is not already in the game
     * @pre the game has less than 5 players
     * @post the player is in the game
     * @post the return String will be null if there was not an error encountered on the server
     */
    public String JoinGame(GameListPresenter presenter, String gameName){

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            //presenter.onError("You must be logged in to create a game!");
            return "You must be logged in to join a game!";
        }

        Game game = clientModelRoot.getGame(gameName);
        if(game.playerExistsInGame(currentUser.getUsername())){
            //presenter.onError("You are already in this game!");
            clientModelRoot.setCurrentGame(game);
            presenter.onJoinGameResponse(true);
            return null;
            //return "You are already in this game!";
        }

        if(game.isGameFull()){
            //presenter.onError("This game is already full!");
            return "This game is already full!";
        }

        System.out.println("Sending Join Game to Proxy");
        LobbyResults results = (LobbyResults) proxy.JoinGame(gameName, currentUser.getUsername());
        System.out.println(results.toString());

        if(results.isSuccess()){
            clientModelRoot.addPlayerToGame(currentUser.getUsername(), gameName);
            clientModelRoot.setCurrentGame(game);
        }else{
            //presenter.onError(results.getErrorInfo());
            return results.getErrorInfo();
        }

        presenter.onJoinGameResponse(results.isSuccess());
        return null;
    }

    /**
     * Starts a game if it has the appropriate number of players in it. This method handles
     * communicating with the client models and with the ServerProxy in order to successfully start
     * a game.
     * @param presenter the Presenter that will be notified of the success of this method
     * @param gameName the name of the game that will be started
     * @pre the Current User is not null
     * @pre presenter != null
     * @pre a game with the name passed in must exist
     * @pre the Current User must have already joined the game that is being started
     * @pre the game being started must have at least 2 players in it
     * @post the game is started
     * @post the GameState and Players are initialized
     * @post the return String will be null if there was not an error encountered on the server
     */
    public String StartGame(GameLobbyPresenter presenter, String gameName){

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            //presenter.onError("You must be logged in to start a game!");
            return "You must be logged in to start a game!";
        }

        Game game = clientModelRoot.getGame(gameName);
        if(!game.playerExistsInGame(currentUser.getUsername())){
            //presenter.onError("You cannot start this game if you are not in it!");
            return "You cannot a start this game if you are not in it!";
        }

        if(!game.hasEnoughPlayersToStart()){
            //presenter.onError("This game does not have enough players!");
            return "This game does not have enough players!";
        }

        System.out.println("Game: " + gameName + " User: " + currentUser.getUsername());
        LobbyResults results = (LobbyResults) proxy.StartGame(gameName, currentUser.getUsername());

        //if(results.isSuccess()){
            clientModelRoot.startGame(gameName);
        //}else{
        //    presenter.onError(results.getErrorInfo());
        //    return results.getErrorInfo();
        //}
        InGameFacade inGameFacade = InGameFacade.getInstance();
        inGameFacade.setupCurrentGameState(getCurrentGame());
        inGameFacade.initializeCurrentPlayer(currentUser);
        inGameFacade.preloadGameState();

        clientModelRoot.setUserState(UserState.IN_GAME);

        presenter.onStartGameResponse(true);
        return null;
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

    /**
     * Retrieve a list of all games from the server.
     * @return a list of all of the games in the lobby from the server
     * @pre this is only to be called as the Game Lobby View is initialized
     * @post all games are loaded into the lobby from the server
     */
    public GameList getAllGames(){
        GameList games = clientModelRoot.getGames();
        if(games.size() == 0){

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    LobbyPollerData pollerData = new LobbyPollerData(0);

                    ClientCommunicator communicator = ClientCommunicator.getInstance();
                    LobbyPollerResults results = (LobbyPollerResults)communicator.send(CommonData.POLLER_URI, pollerData);
                    ClientModelRoot modelRoot = ClientModelRoot.getInstance();

                    if(results.isSuccess()) {
                        System.out.println("Preload Success");
                        // GameList of NEW or CHANGED games
                        GameList games = results.getData();
                        modelRoot.updateGames(games);
                        // The most recent sequence number passed from the server
                        int newSequenceNumber = results.getSequenceNumber();
                        modelRoot.setLobbySequenceNumber(newSequenceNumber);

                        System.out.println(modelRoot.getGames().toString());
                    }else{
                        System.out.println("Not Success");
                    }
                }
            });
            thread.start();
            while(thread.isAlive()){
                //wait
            }
            //preLoadGames();
        }
        return games;
    }

    /**
     * Retrieve all games from the server so that they appear immediately on the screen.
     * @pre this is only to be called as the Game Lobby View is initialized
     * @post all games are loaded into the lobby from the server
     */
    private void preLoadGames(){

        LobbyPollerData pollerData = new LobbyPollerData(0);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        LobbyPollerResults results = (LobbyPollerResults)communicator.send(CommonData.POLLER_URI, pollerData);
        ClientModelRoot modelRoot = ClientModelRoot.getInstance();

        if(results.isSuccess()) {
            System.out.println("Preload Success");
            // GameList of NEW or CHANGED games
            GameList games = results.getData();
            modelRoot.updateGames(games);
            // The most recent sequence number passed from the server
            int newSequenceNumber = results.getSequenceNumber();
            modelRoot.setLobbySequenceNumber(newSequenceNumber);

            System.out.println(modelRoot.getGames().toString());
        }else{
            System.out.println("Not Success");
        }
    }
}


