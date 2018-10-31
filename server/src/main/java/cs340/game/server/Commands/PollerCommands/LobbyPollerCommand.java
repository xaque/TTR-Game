package cs340.game.server.Commands.PollerCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.LobbyCommandLog;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.LobbyPollerData;
import cs340.game.shared.models.GameList;
import cs340.game.shared.results.LobbyPollerResults;
import cs340.game.shared.results.Results;

public class LobbyPollerCommand implements iCommand {

    /**
     * Client polls the server to see if any new updates have occurred. If there are no updates, an
     * error message is returned. If there are updates, a GameList is returned with each update.
     * @param data cast to LobbyPollerData, contains the client's sequence number of its most recent poll
     * @return Results object with boolean stating whether new information was polled, a GameList
     *          of any new info, and an error message if there is no new info
     */
    public Results execute(Data data) {

        LobbyPollerData pData = (LobbyPollerData)data;

        LobbyCommandLog log = LobbyCommandLog.getInstance();

        // Check if the Client is up to date
        int currentSequenceNumber = pData.getSequenceNumber();
        if(currentSequenceNumber == log.getLogLength()){
            return new LobbyPollerResults(false, null, "No new data.");
        }

        int newSequenceNumber = log.getLogLength();
        GameList games = new GameList();
        for(int i = currentSequenceNumber; i < log.getLogLength(); i++){
            games.addGame(log.getIndexedLobbyCommand(i));
        }

        LobbyPollerResults results = new LobbyPollerResults(true, games, "");
        results.setSequenceNumber(newSequenceNumber);
        return results;
    }
}