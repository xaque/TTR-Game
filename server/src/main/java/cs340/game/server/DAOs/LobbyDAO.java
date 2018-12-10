package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.Game;

public interface LobbyDAO {

    public void addGame(String gameName, Game game);
    public void updateGame(String gameName, Game game);
    public Game getGame(String gameName);
    public ArrayList<Game> getAllGames();
}
