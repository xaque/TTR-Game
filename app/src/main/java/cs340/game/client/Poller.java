package cs340.game.client;

import cs340.game.shared.CommandType;
import cs340.game.shared.Results;
import cs340.game.shared.data.PollerData;
import cs340.game.shared.models.GameList;

/**
 * This Class has the purpose of frequently checking with the server to see if anything has changed
 * inside of the app and checks for specific updates depending on the current state of the user.
 */
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

    /**
     * A helper method that handles communicating with the server in order to get the updates to
     * the game lobby. This includes anything that has to do with new and/or changed games.
     * @param lastSequenceNumber a number that identifies the last time that the game lobby was
     *                           updated; this changes on the server's side as other players create
     *                           or join games and the client needs to catch up with the currect
     *                           sequence number on the Server
     */
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

    /**
     * A helper method that handles communicating with the server in order to get the updates to
     * a specific game and its gameplay. This includes anything that has to do with the actual
     * gameplay of a specific game and will sync the data of any moves of any of the players within
     * a game.
     * @param lastSequenceNumber a number that identifies the last time that the game was updated;
     *                           this changes on the server's side as other players take turns or
     *                           do anything to alter the state of the game being played
     */
    public void getGameUpdates(int lastSequenceNumber){
    }
}
