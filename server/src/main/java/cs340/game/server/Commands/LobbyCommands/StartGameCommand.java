package cs340.game.server.Commands.LobbyCommands;

import java.util.ArrayList;
import java.util.List;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.LobbyGameDatabase;
import cs340.game.server.DB.UserDatabase;
import cs340.game.server.Models.ServerGameState;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.User;
import cs340.game.shared.results.LobbyResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 10/27/2018.
 */

public class StartGameCommand implements iCommand {
    public Results execute(Data data) {
        LobbyData lobbyData = (LobbyData)data;
        Game startingGame = LobbyGameDatabase.getInstance().getGame(lobbyData.getGameID());

        // check if user is logged in with an authtoken
        try {
            String authToken = AuthTokenDatabase.getInstance().getAuthToken(lobbyData.getUsername());
        }
        catch(ServerException ex) {
            return new LobbyResults(false, ex.getMessage());
        }

        // check if the game to start has at least 2 players
        if(startingGame.GetGameSize() < 2) {
            ServerException ex = new ServerException("There are not enough players to start this game.");
            return new LobbyResults(false, ex.getMessage());
        }

        List<String> usernames = startingGame.getPlayers();
        List<User> users = new ArrayList<>();
        for(int i = 0; i < usernames.size(); i++) {
            User user = UserDatabase.getInstance().getUserByUsername(usernames.get(i));
            users.add(user);
        }

        ServerGameState gameState = new ServerGameState(lobbyData.getGameID(), users);

        LobbyGameDatabase.getInstance().startGame(startingGame);
        return new LobbyResults(true, null);
    }
}
