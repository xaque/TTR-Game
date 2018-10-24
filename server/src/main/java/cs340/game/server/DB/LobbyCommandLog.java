package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.models.Game;

/**
 * Created by Stephen on 10/1/2018.
 */

public class LobbyCommandLog {
    private List<Game> lobbyCommands;

    private static LobbyCommandLog instance;

    private LobbyCommandLog() {
        lobbyCommands = new ArrayList<>();
    }

    public static LobbyCommandLog getInstance() {
        if(instance == null) {
            instance = new LobbyCommandLog();
        }
        return instance;
    }

    public void addLobbyCommand(Game game) {
        lobbyCommands.add(game);
    }

    /*public List<Game> getLobbyCommands() {
        return lobbyCommands;
    }*/

    public int getLogLength() {
        return lobbyCommands.size();
    }

    public Game getIndexedLobbyCommand(int index) {
        return lobbyCommands.get(index);
    }
}
