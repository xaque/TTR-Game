package cs340.game.server.Commands.LobbyCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.LobbyGameDatabase;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyData;
import cs340.game.shared.models.Game;
import cs340.game.shared.results.LobbyResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 9/28/2018.
 */

public class JoinGameCommand implements iCommand {

    /**
     * Causes player to join game with the given gameID. Returns an error if the game is full.
     * @param data cast to type LobbyData, contains player username and gameID of desired game to join.
     * @return Results object stating success of creating the game and a potential error message
     */
    public Results execute(Data data) {
        LobbyData lobbyData = (LobbyData)data;
        Game game = LobbyGameDatabase.getInstance().getGame(lobbyData.getGameID());
        if(game.GetGameSize() == 5) {
            ServerException ex = new ServerException("This game is already full.");
            return new LobbyResults(false, ex.getMessage());
        }
        LobbyGameDatabase.getInstance().addPlayerToGame(lobbyData.getUsername(), game);
        return new LobbyResults(true, null);
    }
}

