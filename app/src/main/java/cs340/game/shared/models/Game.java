package cs340.game.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game implements Serializable{

    private static final int MIN_GAME_SIZE = 2;
    private static final int MAX_GAME_SIZE = 5;

    private String name;
    private List<String> players = new ArrayList<>();

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
}