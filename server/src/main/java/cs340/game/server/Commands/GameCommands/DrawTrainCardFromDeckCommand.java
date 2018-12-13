package cs340.game.server.Commands.GameCommands;

import cs340.game.server.Commands.CommandHelper;
import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.server.Factories.DAOFactory;
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

public class DrawTrainCardFromDeckCommand implements iCommand {
    public Results execute(Data data, DAOFactory daoFactory) {
        TrainCardData trainCardData = (TrainCardData)data;
        String authToken = trainCardData.getAuthToken();
        //String username = AuthTokenDatabase.getInstance().getUsernameByAuthToken(authToken);
        String username = daoFactory.getUserDAO().getUsernameByAuthToken(authToken);
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(authToken);

        TrainCard drawnCard = game.drawTrainCardFromDeck(username);

        String actionMessage = username + " drew a Train card from the deck.";
        GameHistoryAction action = new GameHistoryAction(actionMessage, null);
        game.addGameCommand(action);

        if(game.getGameState().isOneTrainCardDrawn()) {
            game.endTurn();
        }
        else {
            game.getGameState().setOneTrainCardDrawn(true);
        }

        // Update Database
        CommandHelper.updateGame(daoFactory, game, data);

        return new TrainCardResults(true, drawnCard, null);
    }
}
