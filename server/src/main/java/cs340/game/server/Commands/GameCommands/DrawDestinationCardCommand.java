package cs340.game.server.Commands.GameCommands;

import java.util.ArrayList;

import cs340.game.server.Commands.CommandHelper;
import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.Data;
import cs340.game.shared.data.DestinationCardData;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.results.DestinationCardResults;
import cs340.game.shared.results.Results;

public class DrawDestinationCardCommand implements iCommand {

    @Override
    public Results execute(Data data, DAOFactory daoFactory) {
        DestinationCardData destinationCardData = (DestinationCardData)data;
        String authToken = destinationCardData.getAuthToken();
        //String username = AuthTokenDatabase.getInstance().getUsernameByAuthToken(authToken);
        String username = daoFactory.getUserDAO().getUsernameByAuthToken(authToken);
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(authToken);
        if(game == null) {
            ServerException ex = new ServerException("You are not in an active game.");
            return new DestinationCardResults(false, null, ex.getMessage());
        }
        ArrayList<DestinationCard> drawnCards = game.drawDestinationCards(username);

        String actionMessage = username + " drew " + Integer.toString(drawnCards.size()) + " Destination cards.";
        GameHistoryAction action = new GameHistoryAction(actionMessage, null);
        game.addGameCommand(action);

        // Update Database
        CommandHelper.updateGame(daoFactory, game, data);

        return new DestinationCardResults(true, drawnCards, null);
    }
}