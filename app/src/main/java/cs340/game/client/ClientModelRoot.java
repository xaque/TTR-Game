package cs340.game.client;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.shared.Results;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameList;
import cs340.game.shared.models.User;

public class ClientModelRoot extends Observable {
    // Observable methods:
    // public void addObserver(Observer o)
    // public void deleteObserver(Observer o)
    // public void notifyObservers()
    // public Object getUpdate(Observer o)

    private List<Observer> observers;

    // Start Singleton
    private static ClientModelRoot instance;

    private ClientModelRoot(){}

    public static ClientModelRoot getInstance() {

        if(instance == null){

            instance = new ClientModelRoot();
        }

        return instance;
    }
    // End Singleton

    private User currentUser;
    private GameList games;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {

        if(this.currentUser == null) {
            this.currentUser = currentUser;
        }
    }

    public Game getGame(String gameName){

        return games.getGame(gameName);
    }

    public void addPlayerToGame(String userName, String gameName){

        Game game = games.getGame(gameName);
        game.AddPlayer(userName);
        //notifyObservers();
    }

    public GameList getGames() {
        return games;
    }

    public void addGame(Game game){

        games.addGame(game);
        //notifyObservers();
    }

    @Override
    public void notifyObservers(){
        for(Observer obj : observers) {
            //obj.update();
        }
    }
}
