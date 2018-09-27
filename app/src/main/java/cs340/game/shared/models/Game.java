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

        if(GetGameSize() < MAX_GAME_SIZE) {

            players.add(user);
        }
    }

    public int GetGameSize(){

        return players.size();
    }
}