package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.server.DB.ServerGameState;

public class GameFlatFileDAO extends FlatFileDAO implements GameDAO{
    private static final String name_prefix = "game";
    private static final String name_extension = "fdb";
    private String filename;

    public GameFlatFileDAO(){

    }

    @Override
    public void addGame(String gameName, ServerGameState game) {

    }

    @Override
    public void updateGame(String gameName, ServerGameState game) {

    }

    @Override
    public ServerGameState getGame(String gameName) {
        return null;
    }

    @Override
    public ArrayList<ServerGameState> getAllGames() {
        return null;
    }

    @Override
    protected boolean updateDB() {
        return false;
    }

    @Override
    protected boolean loadDB() {
        return false;
    }
}
