package cs340.game.shared.models;

import java.io.Serializable;
import java.util.List;

/**
 * A container that holds a List of games along with methods to handle the games that it contains.
 */
public class GameList implements Serializable{

    private List<Game> games;

    public List<Game> GetGames() {
        return games;
    }

    public void addGame(Game game){
        games.add(game);
    }

    /**
     * Retrieves a game from the list of games.
     * @param gameName the name of the game that is to be retrieved
     * @return the game object with the name passed in; if the game does not exist, returns null
     */
    public Game getGame(String gameName){

        for(int i = 0; i < games.size(); i++){

            Game game = games.get(i);
            if(game.getName().equals(gameName)){

                return game;
            }
        }

        return null;
    }

    /**
     * Checks if the list of games contains a game with the name that is passed in.
     * @param gameName the name of a game whose presence in the game list is being checked
     * @return true if a game with the given name exists, false otherwise
     */
    public boolean gameExists(String gameName){

        for(int i = 0; i < games.size(); i++){

            Game game = games.get(i);
            if(game.getName().equals(gameName)){

                return true;
            }
        }

        return false;
    }

    public int size(){
        return games.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof GameList)) {
            return false;
        }

        GameList gameList = (GameList) o;
        List<Game> otherGames = gameList.GetGames();
        for(int i = 0; i < games.size(); i++){

            boolean gameExists = false;
            for(int j = 0; j < otherGames.size(); j++){

                if(games.get(i).equals(otherGames.get(j))){

                    gameExists = true;
                }
            }

            if(!gameExists){

                return false;
            }
        }

        return true;
    }
}
