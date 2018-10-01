package cs340.game.server.Commands.LobbyCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.LobbyGameDatabase;
import cs340.game.server.DB.UserDatabase;
import cs340.game.server.LobbyGame;
import cs340.game.server.User;
import cs340.game.shared.Results;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyData;

/**
 * Created by Stephen on 9/28/2018.
 */

public class CreateGameCommand implements iCommand {
    public Results execute(Data data) {
        LobbyData lobbyData = (LobbyData)data;
        //TODO: where to check if user is actually logged in?
        LobbyGame lobbyGame = new LobbyGame(lobbyData.getGameID());
        lobbyGame.addPlayer(lobbyData.getUsername());
        LobbyGameDatabase.getInstance().addGame(lobbyGame);
        //TODO: error if name already exists?
        return new Results(true, null, null);
    }
}
