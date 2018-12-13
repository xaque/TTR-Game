package cs340.game.server.DAOs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.models.Player;

public class GameFlatFileDAO extends FlatFileDAO implements GameDAO{
    HashMap<String,ServerGameState> gameStates = new HashMap<>();
    private static final String filename = "game.fdb";
    private static GameFlatFileDAO instance;

    private GameFlatFileDAO(){
        if (!loadDB()){
            gameStates = new HashMap<>();
        }
    }

    public static GameFlatFileDAO getInstance(){
        if (instance == null){
            instance = new GameFlatFileDAO();
        }
        return instance;
    }

    @Override
    public void addGame(String gameName, ServerGameState game) {
        gameStates.put(gameName, game);
        updateDB();
    }

    @Override
    public void updateGame(String gameName, ServerGameState game) {
        gameStates.put(gameName, game);
        updateDB();
    }

    @Override
    public ServerGameState getGameByUsername(String username) {
        for (Map.Entry<String,ServerGameState> entry : gameStates.entrySet()){
            for (Player p : entry.getValue().getPlayers()){
                if (p.getName().equals(username)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public ServerGameState getGameByAuthToken(String authToken) {
        for (Map.Entry<String,ServerGameState> entry : gameStates.entrySet()){
            for (Player p : entry.getValue().getPlayers()){
                if (p.getAuthToken().equals(authToken)){
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<ServerGameState> getAllGames() {
        return new ArrayList<>(gameStates.values());
    }

    @Override
    protected boolean updateDB() {
        return super.writeObjectToFile(filename, gameStates);
    }

    @Override
    protected boolean loadDB() {
        try{
            gameStates = super.readObjectFromFile(filename, gameStates.getClass());
        }catch (NullPointerException e){
            return false;
        }

        if (gameStates == null){
            return false;
        }
        return true;
    }

    @Override
    public void clearData() {
        super.deleteFile(filename);
        gameStates = new HashMap<>();
    }
}
