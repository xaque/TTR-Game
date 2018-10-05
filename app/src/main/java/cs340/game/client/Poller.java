package cs340.game.client;

import cs340.game.shared.CommandType;
import cs340.game.shared.Results;
import cs340.game.shared.data.PollerData;
import cs340.game.shared.models.GameList;

public class Poller implements Runnable{

    ClientModelRoot modelRoot = ClientModelRoot.getInstance();

    private static final int ONE_SECOND = 1000;

    @Override
    public void run() {

        while(true){

            UserState userState = modelRoot.getUserState();
            try {
                Thread.sleep(ONE_SECOND);

                int lastSequenceNumber;
                if(userState == UserState.IN_LOBBY){

                    lastSequenceNumber = modelRoot.getLobbySequenceNumber();
                    getLobbyUpdates(lastSequenceNumber);

                }else if(userState == UserState.IN_GAME){

                    lastSequenceNumber = modelRoot.getGameSequenceNumber();
                    getGameUpdates(lastSequenceNumber);

                }else if(userState == UserState.LOGGED_OUT){

                    break;
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
        // TODO Update sequence number
        int newSequenceNumber = 0;
        modelRoot.setLobbySequenceNumber(newSequenceNumber);
    }

    public void getGameUpdates(int lastSequenceNumber){
    }
}
