package cs340.game.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 9/28/2018.
 */

public class LobbyGame {
    private String gameID;
    private List<String> joinedPlayers;

    public LobbyGame(String gameID) {
        this.gameID = gameID;
        joinedPlayers = new ArrayList<>();
    }

    public void addPlayer(String username) {
        if(joinedPlayers.size() < 5) {
            joinedPlayers.add(username);
        }
        else {
            //TODO: throw exception?
        }
    }

    public void removePlayer(String username) {
        if(joinedPlayers.contains(username)) {
            joinedPlayers.remove(username);
        }
        else {
            //TODO: throw exception?
        }
    }

    public String getGameID() {
        return this.gameID;
    }
}
