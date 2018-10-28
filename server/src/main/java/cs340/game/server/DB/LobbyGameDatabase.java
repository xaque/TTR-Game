package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.models.Game;

/**
 * Created by Stephen on 9/28/2018.
 */

public class LobbyGameDatabase {
    private List<Game> lobbyGameList;

    private static LobbyGameDatabase instance;

    private LobbyGameDatabase() {
        lobbyGameList = new ArrayList<>();
    }

    public static LobbyGameDatabase getInstance() {
        if(instance == null) {
            instance = new LobbyGameDatabase();
        }
        return instance;
    }

    /**
     * Adds game to database and to LobbyCommandLog
     * @param game the created game to add to the database
     */
    public void addGame(Game game) {
        //System.out.println(game.toString());
        LobbyCommandLog.getInstance().addLobbyCommand(game);
        lobbyGameList.add(game);
    }

    /**
     * Returns game if the given name refers to any game in the database, otherwise returns null
     * @param gameName the name of the game to be searched for
     * @return the game with the given name, or null if no game of that name exists in the DB
     */
    public Game getGame(String gameName) {
        for(int i = 0; i < lobbyGameList.size(); i++) {
            if(lobbyGameList.get(i).getName().equals(gameName)) {
                //System.out.println(lobbyGameList.get(i).toString());
                return lobbyGameList.get(i);
            }
        }
        return null;
    }

    /**
     * Removes game with the given name from the Lobby Database
     * @param gameName name of the game to be removed
     * @return the removed game, or null if the game is not contained in the Lobby
     */
    public Game removeGame(String gameName) {
        for(int i = 0; i < lobbyGameList.size(); i++) {
            if(lobbyGameList.get(i).getName().equals(gameName)) {
                Game removedGame = lobbyGameList.remove(i);
                return removedGame;
            }
        }
        return null;
    }

    /**
     * Adds given username to the game within the database, and adds the join to the LobbyCommandLog
     * @param username username to add to the game
     * @param game game to which player username should be added
     */
    public void addPlayerToGame(String username, Game game) {
        game.AddPlayer(username);
        LobbyCommandLog.getInstance().addLobbyCommand(game);
    }

    public void startGame(Game game) {
        game.setGameStarted(true);
        LobbyCommandLog.getInstance().addLobbyCommand(game);
    }

    /**
     * Clears the database of all added games
     */
    public void clearDatabase(){
        lobbyGameList = new ArrayList<>();
    }

}
