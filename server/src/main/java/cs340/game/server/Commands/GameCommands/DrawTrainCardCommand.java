package cs340.game.server.Commands.GameCommands;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.TrainCardData;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.results.Results;
import cs340.game.shared.results.TrainCardResults;

/**
 * Created by Stephen on 10/31/2018.
 */

public class DrawTrainCardCommand implements iCommand {
    public Results execute(Data data) {
        TrainCardData trainCardData = (TrainCardData)data;
        String authToken = trainCardData.getAuthToken();
        String username = AuthTokenDatabase.getInstance().getUsernameByAuthToken(authToken);
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(authToken);
        if(game == null) {
            ServerException ex = new ServerException("You are not in an active game.");
            return new TrainCardResults(false, null, ex.getMessage());
        }

        //TODO implement this method in ServerGameState once Zach is done
        TrainCard drawnCard = null;
        //TrainCard drawnCard = game.drawTrainCards(username);

        String actionMessage = username + " drew a Train card from the deck.";
        GameHistoryAction action = new GameHistoryAction(actionMessage, null);
        game.addGameCommand(action);

        return new TrainCardResults(true, drawnCard, null);
    }
}
