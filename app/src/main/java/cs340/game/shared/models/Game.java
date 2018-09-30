package cs340.game.shared.models;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable{

    private static final int MIN_GAME_SIZE = 2;
    private static final int MAX_GAME_SIZE = 5;

    private String name;
    private List<String> players;

    public Game(String gameName, String originalUser){

        name = gameName;
        players.add(originalUser);
    }

    public void AddPlayer(String user){

        if(!isGameFull()) {

            players.add(user);
        }
    }

    public boolean playerExistsInGame(String user){

        for(int i = 0; i < players.size(); i++){
            String playerName = players.get(i);
            if(playerName.equals(user)){

                return true;
            }
        }

        return false;
    }

    public boolean isGameFull(){
        return (GetGameSize() >= MAX_GAME_SIZE);
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
}