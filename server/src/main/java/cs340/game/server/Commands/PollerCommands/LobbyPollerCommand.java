package cs340.game.server.Commands.PollerCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.LobbyCommandLog;
import cs340.game.shared.LobbyPollerResults;
import cs340.game.shared.Results;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.PollerData;
import cs340.game.shared.models.GameList;

public class LobbyPollerCommand implements iCommand {

    public Results execute(Data data) {

        PollerData pData = (PollerData)data;

        LobbyCommandLog log = LobbyCommandLog.getInstance();

        // Check if the Client is up to date
        if(pData.getSequenceNumber() == log.getLogLength()){
            return new LobbyPollerResults(false, null, "No new data.");
        }

        int newSequenceNumber = log.getLogLength();
        GameList games = new GameList();
        for(int i = 0; i < log.getLobbyCommands().size(); i++){
            games.addGame(log.getIndexedLobbyCommand(i));
        }

        LobbyPollerResults results = new LobbyPollerResults(true, games, "");
        results.setSequenceNumber(newSequenceNumber);
        return results;
    }
}