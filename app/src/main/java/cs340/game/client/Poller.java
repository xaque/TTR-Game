package cs340.game.client;

import cs340.game.shared.CommandType;
import cs340.game.shared.Results;
import cs340.game.shared.data.PollerData;
import cs340.game.shared.models.GameList;

public class Poller {

    ClientModelRoot modelRoot = ClientModelRoot.getInstance();

    public Results getLobbyUpdates(int lastSequenceNumber){

        PollerData pollerData = new PollerData(CommandType.LOBBY_POLL, lastSequenceNumber);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send("/poller", pollerData);

        // GameList of NEW or CHANGED games
        GameList games = new GameList();
        modelRoot.updateGames(games);

        return null;
    }
}
