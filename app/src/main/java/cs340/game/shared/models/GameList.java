package cs340.game.shared.models;

import java.io.Serializable;
import java.util.List;

public class GameList implements Serializable{

    private List<Game> games;

    public List<Game> GetGames() {

        return games;
    }

    public void addGame(Game game){

        games.add(game);
    }

    public Game getGame(String gameName){

        for(int i = 0; i < games.size(); i++){

            Game game = games.get(i);
            if(game.getName().equals(gameName)){

                return game;
            }
        }

        return null;
    }

    public boolean gameExists(String gameName){

        for(int i = 0; i < games.size(); i++){

            Game game = games.get(i);
            if(game.getName().equals(gameName)){

                return true;
            }
        }

        return false;
    }
}
