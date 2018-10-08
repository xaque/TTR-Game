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

    public void addGame(Game game) {
        //System.out.println(game.toString());
        LobbyCommandLog.getInstance().addLobbyCommand(game);
        lobbyGameList.add(game);
    }

    public void removeGame(Game game) {
        game.RemoveAllPlayers();
        LobbyCommandLog.getInstance().addLobbyCommand(game);
        lobbyGameList.remove(game);
    }

    public Game getGame(String gameName) {
        for(int i = 0; i < lobbyGameList.size(); i++) {
            if(lobbyGameList.get(i).getName().equals(gameName)) {
                //System.out.println(lobbyGameList.get(i).toString());
                return lobbyGameList.get(i);
            }
        }
        return null;
    }

    public void addPlayerToGame(String username, Game game) {
        game.AddPlayer(username);
        LobbyCommandLog.getInstance().addLobbyCommand(game);
    }
}
