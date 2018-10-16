package cs340.game.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for all information relevant to a game.
 */
public class Game implements Serializable{

    private static final int MIN_GAME_SIZE = 2;
    private static final int MAX_GAME_SIZE = 5;

    private String name;
    private List<String> players = new ArrayList<>();
    private boolean gameStarted = false;

    private List<Route> routes = new ArrayList<>();

    public Game(String gameName, String originalUser){

        name = gameName;
        players.add(originalUser);
    }

    public void AddPlayer(String user){

        if(!isGameFull()) {

            players.add(user);
        }
    }


    public void RemovePlayer(String user) {
        if(playerExistsInGame(user)) {
            players.remove(user);
        }
    }

    public void RemoveAllPlayers() {
        players.clear();
    }

    /**
     * Checks if the given player is already part of the game.
     * @param username the name of the user whose presence in the game is being checked
     * @return true if the player is in the game, false otherwise
     */
    public boolean playerExistsInGame(String username){

        for(int i = 0; i < players.size(); i++){
            String playerName = players.get(i);
            if(playerName.equals(username)){

                return true;
            }
        }

        return false;
    }

    public boolean isGameFull(){
        return (GetGameSize() >= MAX_GAME_SIZE);
    }

    public boolean hasEnoughPlayersToStart(){
        return (GetGameSize() >= MIN_GAME_SIZE);
    }

    public int GetGameSize(){
        return players.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayers(){
        return players;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Game)) {
            return false;
        }

        Game game = (Game) o;
        List<String> otherPlayers = new ArrayList<>();
        if(players.size() != otherPlayers.size()){
            return false;
        }

        for(int i = 0; i < players.size(); i++){

            boolean found = false;
            for(int j = 0; j < otherPlayers.size(); j++){

                if(players.get(i).equals(otherPlayers.get(j))){

                    found = true;
                }
            }

            if(!found){
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", players=" + players.toString() +
                ", gameStarted=" + gameStarted +
                '}';
    }
}