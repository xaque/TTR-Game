package cs340.game.server.Commands.GameCommands;

import java.util.List;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.Models.ServerGameState;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.DestinationCardData;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.results.DestinationCardResults;
import cs340.game.shared.results.Results;
import cs340.game.shared.data.Data;

public class DrawDestincationCardCommand implements iCommand {

    //TODO implement override stub method
    @Override
    public Results execute(Data data) {
        DestinationCardData destinationCardData = (DestinationCardData)data;
        String authtoken = destinationCardData.getAuthToken();
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByAuthToken(authtoken);
        if(game == null) {
            ServerException ex = new ServerException("You are not in an active game.");
            return new DestinationCardResults(false, null, ex.getMessage());
        }
        List<DestinationCard> drawnCards = game.drawDestinationCards();
        return new DestinationCardResults(true, drawnCards, null);
    }
}
