package cs340.game.server.Commands.PollerCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.GameCommandLog;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.GamePollerData;
import cs340.game.shared.results.GamePollerResults;
import cs340.game.shared.results.Results;

public class GamePollerCommand implements iCommand {
    public Results execute(Data data) {
        GamePollerData pData = (GamePollerData)data;
        //ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(pData.getAuthtoken());
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByUsername(pData.getAuthtoken());
        GameCommandLog log = game.getCommandLog();

        // Check if the Client is up to date
        int currentSequenceNumber = pData.getSequenceNumber();
        if(currentSequenceNumber == log.getLogLength()){
            //return new GamePollerResults(false, null, currentSequenceNumber, "No new data.");
        }

        int newSequenceNumber = log.getLogLength();
        //Not using action list anymore. GameState instead
        /*
        GameHistoryActionList actions = new GameHistoryActionList();
        for(int i = currentSequenceNumber + 1; i < log.getLogLength(); i++) {
            actions.addAction(log.getGameCommandAtIndex(i));
        }*/

        for(int i = 0; i < game.getCommandLog().getLogLength(); i++){
            System.out.println(game.getCommandLog().getGameCommandAtIndex(i).getActionMessage());
        }
        //TODO any errors to keep track of?
        GamePollerResults results = new GamePollerResults(true, game.getGameState(), newSequenceNumber, null);
        return results;
    }
}
