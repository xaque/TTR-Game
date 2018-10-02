package cs340.game.client;

import cs340.game.shared.CommandType;
import cs340.game.shared.Results;
import cs340.game.shared.data.PollerData;
import cs340.game.shared.models.GameList;

public class Poller implements Runnable{

    ClientModelRoot modelRoot = ClientModelRoot.getInstance();

    @Override
    public void run() {
        while(true){

            UserState userState = modelRoot.getUserState();
            try {
                Thread.sleep(1000);

                if(userState == UserState.IN_LOBBY){

                    // TODO retrieve last sequence number (where should this be stored?)
                    getLobbyUpdates(0);
                }

            }catch (Exception ex){
            }
        }
    }

    public void getLobbyUpdates(int lastSequenceNumber){

        PollerData pollerData = new PollerData(CommandType.LOBBY_POLL, lastSequenceNumber);

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = communicator.send("/poller", pollerData);

        // GameList of NEW or CHANGED games
        GameList games = new GameList();
        modelRoot.updateGames(games);
    }
}
