package cs340.game.server.Commands.GameCommands;

import java.util.ArrayList;

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

public class DiscardTrainCardsCommand implements iCommand {
    public Results execute(Data data, DAOFactory daoFactory) {
        TrainCardData trainCardData = (TrainCardData)data;
        String authToken = trainCardData.getAuthToken();
        String username = AuthTokenDatabase.getInstance().getUsernameByAuthToken(authToken);
        ArrayList<TrainCard> discardedCards = trainCardData.getCards();
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(authToken);
        if(game == null) {
            ServerException ex = new ServerException("You are not in an active game.");
            return new TrainCardResults(false, null, ex.getMessage());
        }

        game.discardTrainCards(discardedCards, username);

        String actionMessage = username + " discarded " + Integer.toString(discardedCards.size()) + " Train cards.";
        GameHistoryAction action = new GameHistoryAction(actionMessage, null);
        game.addGameCommand(action);

        // Update Database
        CommandHelper.updateGame(daoFactory, game, data);

        return new TrainCardResults(true, null, null);
    }
}
