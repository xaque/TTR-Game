package cs340.game.server.Commands.GameCommands;

import java.util.ArrayList;
import java.util.List;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.AuthTokenDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.DestinationCardData;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.results.DestinationCardResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 10/28/2018.
 */

public class ReturnDestinationCardCommand implements iCommand {
    public Results execute(Data data) {
        DestinationCardData destinationCardData = (DestinationCardData)data;
        String authToken = destinationCardData.getAuthToken();
        String username = AuthTokenDatabase.getInstance().getUsernameByAuthToken(authToken);
        ArrayList<DestinationCard> returnedCards = destinationCardData.getCards();
        //ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(authToken);
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByUsername(authToken);
        if(game == null) {
            ServerException ex = new ServerException("You are not in an active game.");
            return new DestinationCardResults(false, null, ex.getMessage());
        }
        try {
            game.returnDestinationCards(returnedCards, username);

            String actionMessage = username + " returned " + Integer.toString(returnedCards.size()) + " Destination cards to the deck.";
            GameHistoryAction action = new GameHistoryAction(actionMessage, null);
            game.addGameCommand(action);
            return new DestinationCardResults(true, null, null);
        }
        catch(Exception exception) {
            ServerException ex = new ServerException("You do not have the cards you are trying to return.");
            return new DestinationCardResults(false, null, ex.getMessage());
        }
    }
}
