package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.server.DB.ServerGameState;

public class GameFlatFileDAO extends FlatFileDAO implements GameDAO{
    private static final String filename = "game.fdb";
    private static GameFlatFileDAO instance;

    private GameFlatFileDAO(){

    }

    public static GameFlatFileDAO getInstance(){
        if (instance == null){
            instance = new GameFlatFileDAO();
        }
        return instance;
    }

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
    protected boolean updateDB() {
        return false;
    }

    @Override
    protected boolean loadDB() {
        return false;
    }

    @Override
    public void clearData() {

    }
}
