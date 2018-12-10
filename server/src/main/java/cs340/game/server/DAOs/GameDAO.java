package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.server.DB.ServerGameState;

public interface GameDAO {

    public void addGame(String gameName, ServerGameState game);
    public void updateGame(String gameName, ServerGameState game);
    public ServerGameState getGame(String gameName);
    public ArrayList<ServerGameState> getAllGames();
}
