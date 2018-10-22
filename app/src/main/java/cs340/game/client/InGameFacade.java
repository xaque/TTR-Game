package cs340.game.client;

import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.TrainCard;

/**
 * Handles communication with the ClientModelRoot object. Meant to limit dependencies between the
 * Presenters and the Models. Used for actions happening inside of a game.
 */
public class InGameFacade {

    // Start Singleton
    private static InGameFacade instance;

    private InGameFacade(){}

    public static InGameFacade getInstance() {

        if(instance == null){

            instance = new InGameFacade();
        }

        return instance;
    }
    // End Singleton

    private ServerProxy proxy = new ServerProxy();
    private ClientModelRoot clientModelRoot = ClientModelRoot.getInstance();

    // DRAWING
    public String DrawTrainCardFromDeck(){

        TrainCard newCard = new TrainCard(Color.BLUE);
        Player currentPlayer = clientModelRoot.getCurrentPlayer();
        currentPlayer.addTrainCard(newCard);

        return null;
    }

    public String DrawDestinationCards(){

        DestinationCard newCard = new DestinationCard(City.DENVER, City.SALT_LAKE);
        Player currentPlayer = clientModelRoot.getCurrentPlayer();
        currentPlayer.addDestinationCard(newCard);

        return null;
    }

    // CHAT

    public String SendMessage(String message){

        return null;
    }
}
