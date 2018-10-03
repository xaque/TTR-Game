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

    public void Login(MainActivityPresenter presenter, String username, String password){

        if(clientModelRoot.getCurrentUser() != null){
            presenter.onError("Already logged in!");
            return;
        }

        User user = new User(username, password);
        Results results = proxy.Login(user);

        if(results.isSuccess()) {
            clientModelRoot.setCurrentUser(user);
            clientModelRoot.setUserState(UserState.IN_LOBBY);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        // TODO Start Poller
        presenter.onLoginResponse(results.isSuccess());
    }

    public void Register(MainActivityPresenter presenter, String username, String password){

        if(clientModelRoot.getCurrentUser() != null){
            presenter.onError("Already logged in!");
            return;
        }

        User user = new User(username, password);
        Results results = proxy.Register(user);

        if(results.isSuccess()) {
            clientModelRoot.setCurrentUser(user);
            clientModelRoot.setUserState(UserState.IN_LOBBY);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        // TODO Start Poller
        presenter.onRegisterResponse(results.isSuccess());
    }

    public void Logout(){

        clientModelRoot.setCurrentUser(null);
        clientModelRoot.setUserState(UserState.LOGGED_OUT);
        // TODO Stopping Poller
    }

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

        Results results = proxy.CreateGame(gameName, currentUser.getUsername());

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

        Results results = proxy.JoinGame(gameName, currentUser.getUsername());

        if(results.isSuccess()){
            clientModelRoot.addPlayerToGame(currentUser.getUsername(), gameName);
            clientModelRoot.setCurrentGame(game);
        }else{
            presenter.onError(results.getErrorInfo());
            return;
        }

        presenter.onCreateGameResponse(results.isSuccess());
    }

    public void StartGame(GameListPresenter presenter, String gameName){

        Game game = clientModelRoot.getGame(gameName);
        game.setGameStarted(true);
    }

    public void addObserver(Observer o){

        clientModelRoot.addObserver(o);
    }

    public void deleteObserver(Observer o){

        clientModelRoot.deleteObserver(o);
    }

    public Game getCurrentGame(){

        return null;
    }
}