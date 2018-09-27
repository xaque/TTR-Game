package cs340.game.shared.models;

import java.io.Serializable;
import java.util.List;

public class GameList implements Serializable{

    private List<Game> games;

    public List<Game> GetGames() {

        return games;
    }

    public void AddGame(Game game){

        games.add(game);
    }
}
