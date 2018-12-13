package cs340.game.server.DAOs;

import java.util.ArrayList;

import cs340.game.shared.models.Game;

public class LobbyFlatFileDAO extends FlatFileDAO implements LobbyDAO {
    private static final String filename = "lobby.fdb";
    private ArrayList<Game> lobbyGameList = new ArrayList<>();
    private static LobbyFlatFileDAO instance;

    private LobbyFlatFileDAO(){
        if (!loadDB()){
            lobbyGameList = new ArrayList<>();
        }
    }

    public static LobbyFlatFileDAO getInstance(){
        if (instance == null){
            instance = new LobbyFlatFileDAO();
        }
        return instance;
    }

    @Override
    public void addGame(Game game) {
        lobbyGameList.add(game);
        updateDB();
    }

    @Override
    public void addPlayerToGame(String username, Game game) {
        //TODO need to make sure the number of players in the game isnt already max?
        game.AddPlayer(username);
        updateDB();
    }

    @Override
    public Game getGame(String gameName) {
        for (Game g : lobbyGameList){
            if (g.getName().equals(gameName)){
                return g;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Game> getAllGames() {
        return lobbyGameList;
    }

    @Override
    public void startGame(Game game) {
        //TODO need to validate here?
        game.setGameStarted(true);
        updateDB();
    }

    @Override
    protected boolean updateDB() {
        return super.writeObjectToFile(filename, lobbyGameList);
    }

    @Override
    protected boolean loadDB() {
        lobbyGameList = super.readObjectFromFile(filename, lobbyGameList.getClass());
        if (lobbyGameList == null){
            return false;
        }
        return true;
    }

    @Override
    public void clearData() {
        super.deleteFile(filename);
        lobbyGameList = new ArrayList<>();
    }
}
