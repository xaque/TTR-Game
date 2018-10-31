package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.models.Player;

/**
 * Created by Stephen on 10/24/2018.
 */

public class ActiveGamesDatabase {
    private List<ServerGameState> games;

    private static ActiveGamesDatabase instance;

    public static ActiveGamesDatabase getInstance() {
        if(instance == null) {
            instance = new ActiveGamesDatabase();
        }
        return instance;
    }

    private ActiveGamesDatabase() {
        games = new ArrayList<>();
    }

    public void addGame(ServerGameState game) {
        games.add(game);
    }

    public ServerGameState getGameByAuthToken(String authtoken) {
        for(int i = 0; i < games.size(); i++) {
            ServerGameState game = games.get(i);
            for(int j = 0; j < game.getPlayers().size(); j++) {
                Player player = game.getPlayers().get(j);
                if(authtoken.equals(player.getAuthToken())) {
                    return game;
                }
            }
        }
        return null;
    }

    public ServerGameState getGameByUsername(String username) {
        for(int i = 0; i < games.size(); i++) {
            ServerGameState game = games.get(i);
            for(int j = 0; j < game.getPlayers().size(); j++) {
                Player player = game.getPlayers().get(j);
                if(username.equals(player.getName())) {
                    return game;
                }
            }
        }
        return null;
    }
}
