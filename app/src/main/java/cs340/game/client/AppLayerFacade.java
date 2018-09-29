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

    private User currentUser;
    private Map<String, Game> games;
    private ServerProxy proxy;

    public void Login(String username, String password){

        User user = new User(username, password);
        Results results = proxy.Login(user);

        SetCurrentUser(user);
    }

    public void Register(String username, String password){

        User user = new User(username, password);
        Results results = proxy.Register(user);

        SetCurrentUser(user);
    }

    public void Logout(){

        currentUser = null;
    }

    private void SetCurrentUser(User user){

        if(currentUser == null) {

            currentUser = user;
        }
    }

    public void CreateGame(String gameName){

        //Game game = new Game(gameName, currentUser.getUsername());
        //games.put(gameName, game);
        proxy.CreateGame(gameName, currentUser.getUsername());
    }

    public void JoinGame(String gameName){

        //Game game = games.get(gameName);
        //game.AddPlayer(currentUser.getUsername());
        proxy.JoinGame(gameName, currentUser.getUsername());
    }
}