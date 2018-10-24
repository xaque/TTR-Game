package cs340.game.server.Commands.PollerCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.GameCommandLog;
import cs340.game.shared.GameHistoryActionList;
import cs340.game.shared.data.PollerData;
import cs340.game.shared.results.GamePollerResults;
import cs340.game.shared.results.Results;
import cs340.game.shared.data.Data;

//TODO stub for phase 2 implementation
public class GamePollerCommand implements iCommand {
    public Results execute(Data data) {
        PollerData pData = (PollerData)data;
        GameCommandLog log = GameCommandLog.getInstance();

        // Check if the Client is up to date
        int currentSequenceNumber = pData.getSequenceNumber();
        if(currentSequenceNumber == log.getLogLength()){
            return new GamePollerResults(false, null, currentSequenceNumber, "No new data.");
        }

        int newSequenceNumber = log.getLogLength();
        GameHistoryActionList actions = new GameHistoryActionList();
        for(int i = currentSequenceNumber + 1; i < log.getLogLength(); i++) {
            actions.addAction(log.getGameCommandAtIndex(i));
        }

        //TODO any errors to keep track of?
        GamePollerResults results = new GamePollerResults(true, actions, newSequenceNumber, null);
        return results;
    }
}
