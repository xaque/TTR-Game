package cs340.game.server.Commands.GameCommands;

import java.util.ArrayList;

import cs340.game.server.Commands.iCommand;
import cs340.game.server.DB.ActiveGamesDatabase;
import cs340.game.server.DB.ServerGameState;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.ServerException;
import cs340.game.shared.data.ClaimRouteData;
import cs340.game.shared.data.Data;
import cs340.game.shared.models.Route;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.results.ClaimRouteResults;
import cs340.game.shared.results.Results;

/**
 * Created by Stephen on 11/11/2018.
 */

public class ClaimRouteCommand implements iCommand {
    public Results execute(Data data, DAOFactory daoFactory) {
        ClaimRouteData claimRouteData = (ClaimRouteData)data;
        String username = claimRouteData.getUsername();
        ServerGameState game = ActiveGamesDatabase.getInstance().getGameByUsername(username);
        if(game == null) {
            ServerException ex = new ServerException("You are not in an active game.");
            return new ClaimRouteResults(false, ex.getMessage());
        }

        try {
            Route routeToClaim = claimRouteData.getRoute();
            ArrayList<TrainCard> cardsToDiscard = new ArrayList<>();

            // if the route is not a grey route
            if(claimRouteData.getColor() == null) {
                for (int i = 0; i < routeToClaim.getLength(); i++) {
                    cardsToDiscard.add(new TrainCard(routeToClaim.getColor()));
                }
            }
            // else if the route is a grey route
            else {
                for(int i = 0; i < routeToClaim.getLength(); i++) {
                    cardsToDiscard.add(new TrainCard(claimRouteData.getColor()));
                }
            }
            game.discardTrainCards(cardsToDiscard, username);
            game.claimRoute(claimRouteData.getRoute(), username);

            String city1 = claimRouteData.getRoute().getCity1().toString();
            String city2 = claimRouteData.getRoute().getCity2().toString();
            String actionMessage = username + " claimed route between " + city1 + " and " + city2 + ".";
            GameHistoryAction action = new GameHistoryAction(actionMessage, null);
            game.addGameCommand(action);
            game.endTurn();

            return new ClaimRouteResults(true, null);
        }
        catch(ServerException ex) {
            return new ClaimRouteResults(false, ex.getMessage());
        }
    }
}
