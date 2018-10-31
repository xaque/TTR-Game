package cs340.game.server.Commands.PollerCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.GameCommandLog;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.GamePollerData;
import cs340.game.shared.models.GameState;
import cs340.game.shared.results.GamePollerResults;
import cs340.game.shared.results.Results;

public class GamePollerCommand implements iCommand {
    public Results execute(Data data) {
        GamePollerData pData = (GamePollerData)data;
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(pData.getAuthtoken());
        GameState gameState = game.getGameState();

        // Check if the Client is up to date
        int currentSequenceNumber = pData.getSequenceNumber();
        if(currentSequenceNumber == gameState.getHistory().getSize()){
            return new GamePollerResults(false, null, currentSequenceNumber, "No new data.");
        }
        else {
            int newSequenceNumber = gameState.getHistory().getSize();
            return new GamePollerResults(true, gameState, newSequenceNumber, null);
        }
        //TODO any errors to keep track of?
    }
}
