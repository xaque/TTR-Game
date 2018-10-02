package cs340.game.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameList;
import cs340.game.shared.models.User;

public class ClientModelRoot extends Observable {
    // Observable methods:
    // public void addObserver(Observer o)
    // public void deleteObserver(Observer o)
    // public void notifyObservers()

    private List<Observer> observers = new ArrayList<>();

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

    private UserState userState = UserState.LOGGED_OUT;
    private User currentUser;
    private GameList games = new GameList();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {

        if(this.currentUser == null) {
            this.currentUser = currentUser;
        }
    }

    public UserState getUserState(){
        return userState;
    }

    public void setUserState(UserState userState){
        this.userState = userState;
    }

    public Game getGame(String gameName){

        return games.getGame(gameName);
    }

    public void addPlayerToGame(String userName, String gameName){

        Game game = games.getGame(gameName);
        game.AddPlayer(userName);

        notifyObservers();
    }

    public GameList getGames() {
        return games;
    }

    public void addGame(Game game){

        games.addGame(game);

        notifyObservers();
    }

    public void updateGames(GameList newGames){

        boolean changed = false;
        List<Game> _newGames = newGames.GetGames();
        for(int i = 0; i < _newGames.size(); i++){

            Game newGame = _newGames.get(i);
            String gameName = newGame.getName();
            if(games.gameExists(gameName)){

                List<String> newPlayers = newGame.getPlayers();
                if(addNewPlayersToGame(gameName, newPlayers)){

                    changed = true;
                }
            }else{

                games.addGame(newGame);
                changed = true;
            }
        }

        if(changed){

            notifyObservers();
        }
    }

    private boolean addNewPlayersToGame(String gameName, List<String> newPlayers){

        boolean playerAdded = false;
        Game currentGame = games.getGame(gameName);
        for(int i = 0; i < newPlayers.size(); i++){

            String playerName = newPlayers.get(i);
            if(!currentGame.playerExistsInGame(playerName)){

                currentGame.AddPlayer(playerName);
                playerAdded = true;
            }
        }

        return playerAdded;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(){

        for(Observer obj : observers) {
            obj.update(this, games);
        }
    }
}
