package cs340.game.client;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import cs340.game.shared.*;
import cs340.game.shared.models.*;

public class AppLayerFacade extends Observable{
    // Observable methods:
    // public void addObserver(Observer o)
    // public void deleteObserver(Observer o)
    // public void notifyObservers()

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

    public boolean Login(String username, String password) throws Exception{

        if(clientModelRoot.getCurrentUser() != null){
            throw new Exception("Already logged in!");
        }

        User user = new User(username, password);
        Results results = proxy.Login(user);

        if(results.isSuccess()) {
            clientModelRoot.setCurrentUser(user);
        }else{
            throw new Exception(results.getErrorInfo());
        }

        return results.isSuccess();
    }

    public boolean Register(String username, String password) throws Exception{

        if(clientModelRoot.getCurrentUser() != null){
            throw new Exception("Already logged in!");
        }

        User user = new User(username, password);
        Results results = proxy.Register(user);

        if(results.isSuccess()) {
            clientModelRoot.setCurrentUser(user);
        }else{
            throw new Exception(results.getErrorInfo());
        }

        return results.isSuccess();
    }

    public void Logout(){

        clientModelRoot.setCurrentUser(null);
    }

    public GameList CreateGame(String gameName) throws Exception{

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            throw new Exception("You must be logged in to create a game!");
        }

        GameList games = clientModelRoot.getGames();
        if(games.gameExists(gameName)){
            throw new Exception("A game with this name already exists!");
        }

        Results results = proxy.CreateGame(gameName, currentUser.getUsername());

        if(results.isSuccess()) {
            Game game = new Game(gameName, currentUser.getUsername());
            clientModelRoot.addGame(game);
        }else{
            throw new Exception(results.getErrorInfo());
        }

        return clientModelRoot.getGames();
    }

    public boolean JoinGame(String gameName) throws Exception{

        User currentUser = clientModelRoot.getCurrentUser();
        if(currentUser == null){
            throw new Exception("You must be logged in to create a game!");
        }

        Game game = clientModelRoot.getGame(gameName);
        if(game.playerExistsInGame(currentUser.getUsername())){
            throw new Exception("You are already in this game!");
        }

        if(game.isGameFull()){
            throw new Exception("This game is already full!");
        }

        Results results = proxy.JoinGame(gameName, currentUser.getUsername());

        if(results.isSuccess()){
            clientModelRoot.addPlayerToGame(currentUser.getUsername(), gameName);
        }else{
            throw new Exception(results.getErrorInfo());
        }

        return results.isSuccess();
    }
}