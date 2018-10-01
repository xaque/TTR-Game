package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.server.LobbyGame;

/**
 * Created by Stephen on 9/28/2018.
 */

public class LobbyGameDatabase {
    private List<LobbyGame> lobbyGameList;

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

    public void addGame(LobbyGame game) {
        lobbyGameList.add(game);
    }

    public void removeGame(LobbyGame game) {
        lobbyGameList.remove(game);
    }

    public LobbyGame getGame(String gameName) {
        for(int i = 0; i < lobbyGameList.size(); i++) {
            if(lobbyGameList.get(i).getGameID().equals(gameName)) {
                return lobbyGameList.get(i);
            }
        }
        //TODO error if game doesn't exist?
        return null;
    }
}
