package cs340.game.server.Commands.LobbyCommands;

import cs340.game.server.DB.LobbyGameDatabase;
import cs340.game.shared.LobbyResults;
import cs340.game.shared.Results;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyData;

/**
 * Created by Stephen on 9/28/2018.
 */

public class JoinGameCommand implements iLobbyCommand {
    public Results execute(Data data) {
        LobbyData lobbyData = (LobbyData)data;
        if(LobbyGameDatabase.getInstance().getGame(lobbyData.getGameID()).GetGameSize() == 5) {
            ServerException ex = new ServerException("This game is already full.");
            return new LobbyResults(false, ex.getMessage());
        }
        LobbyGameDatabase.getInstance().getGame(lobbyData.getGameID()).AddPlayer(lobbyData.getUsername());
        return new LobbyResults(true, null);
    }
}

