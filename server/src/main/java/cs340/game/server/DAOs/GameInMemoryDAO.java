package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.server.DB.ServerGameState;

public class GameInMemoryDAO implements GameDAO{

    @Override
    public void addGame(String gameName, ServerGameState game) {

    }

    @Override
    public void updateGame(String gameName, ServerGameState game) {

    }

    @Override
    public ServerGameState getGameByUsername(String username) {
        return null;
    }

    @Override
    public ServerGameState getGameByAuthToken(String authToken) {
        return null;
    }

    @Override
    public ArrayList<ServerGameState> getAllGames() {
        return null;
    }

    @Override
    public void clearData() {

    }
}
