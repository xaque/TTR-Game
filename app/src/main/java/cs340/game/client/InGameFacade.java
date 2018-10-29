package cs340.game.client;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;
import cs340.game.shared.results.GameResults;

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

    public void setupCurrentGameState(Game game){
        clientModelRoot.InitializeGameState(game);
    }

    // DRAWING
    public String DrawTrainCardFromDeck(){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        GameResults results = (GameResults)proxy.DrawTrainCard(currentPlayer.getAuthToken());

        if(results.isSuccess()) {
            // TODO This will be added to the player's hand through the Poller
            //currentPlayer.addTrainCard(newCard);
        }else{
            return results.getErrorInfo();
        }

        return null;
    }

    public String DiscardTrainCards(Color color, int numberToDiscard){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();
        if(!currentPlayer.hasSufficientCards(color, numberToDiscard)){
            return "You do not have enough cards to do this!";
        }

        try {
            for (int i = 0; i < numberToDiscard; i++) {

                TrainCard card = currentPlayer.getTrainCard(color);
                GameResults results = (GameResults)proxy.DiscardTrainCard(currentPlayer.getAuthToken(), card);

                if(!results.isSuccess()) {
                    return results.getErrorInfo();
                }
            }

            for(int i = 0; i < numberToDiscard; i++){
                currentPlayer.removeTrainCard(color);
            }
        }catch (Exception e){
            return e.getMessage();
        }

        return null;
    }

    public String DrawDestinationCards(){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        GameResults results = (GameResults)proxy.DrawDestinationCard(currentPlayer.getAuthToken());

        if(results.isSuccess()) {
            // TODO This will be added to the player's hand through the Poller
            //currentPlayer.addDestinationCard(newCard);
        }else{
            return results.getErrorInfo();
        }

        return null;
    }

    public String DiscardDestinationCards(List<DestinationCard> cards){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        GameResults results = (GameResults)proxy.DiscardDestinationCards(currentPlayer.getAuthToken(), cards);

        if(results.isSuccess()){
            try {
                currentPlayer.removeDestinationCards(cards);
            }catch (Exception e){
                return e.getMessage();
            }
        }else{
            return results.getErrorInfo();
        }

        return null;
    }

    // CHAT
    public String SendMessage(String message){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        GameResults results = (GameResults)proxy.SendChat(currentPlayer.getAuthToken(), message);

        if(!results.isSuccess()) {
            return results.getErrorInfo();
        }

        return null;
    }

    public GameState getCurrentGame() {
        return clientModelRoot.getCurrentGameState();
    }

    public void setCurrentGame(GameState currentGame) {
        clientModelRoot.setCurrentGameState(currentGame);
    }
}
