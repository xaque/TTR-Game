package cs340.game.server.Commands.PollerCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.GamePollerData;
import cs340.game.shared.models.GameState;
import cs340.game.shared.results.GamePollerResults;
import cs340.game.shared.results.Results;

public class GamePollerCommand implements iCommand {
    public Results execute(Data data) {
        GamePollerData pData = (GamePollerData)data;
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByUsername(pData.getAuthtoken());
        GameState gameState = game.getGameState();

        for(int i = 0; i < gameState.getHistory().getSize(); i++){
            System.out.println(gameState.getHistory().getActions().get(i).getActionMessage());
        }

        // Check if the Client is up to date
        int currentSequenceNumber = pData.getSequenceNumber();
        System.out.println(currentSequenceNumber);
        System.out.println(Integer.toString(gameState.getHistory().getSize()));
        /*if(currentSequenceNumber == gameState.getHistory().getSize()){
            System.out.println("here");
            return new GamePollerResults(false, null, currentSequenceNumber, "No new data.");
        }
        else {*/
            System.out.println("there");
            int newSequenceNumber = gameState.getHistory().getSize();
            return new GamePollerResults(true, gameState, newSequenceNumber, null);
        //}
        //TODO any errors to keep track of?
    }
}
