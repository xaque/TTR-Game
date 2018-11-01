package cs340.game.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

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

    public void initializeCurrentPlayer(User user){
        Player currentPlayer = new Player(user.getUsername(), user.getAuthToken());
        clientModelRoot.setCurrentPlayer(currentPlayer);
    }


    public void addObserver(Observer o){
        clientModelRoot.addObserver(o);
    }

    public void deleteObserver(Observer o){
        clientModelRoot.deleteObserver(o);
    }

    // DRAWING / DISCARDING
    public String drawTrainCardFromDeck(){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        //GameResults results = (GameResults)proxy.DrawTrainCard(currentPlayer.getAuthToken());

        //if(results.isSuccess()) {
            // TODO This will be added to the player's hand through the Poller
            TrainCard newCard = new TrainCard(Color.BLUE);
            clientModelRoot.addTrainCardToPlayer(currentPlayer, newCard);
        //}else{
        //    return results.getErrorInfo();
        //}

        return null;
    }

    public String drawFaceUpTrainCard(TrainCard card){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        //GameResults results = (GameResults)proxy.DrawFaceUpTrainCard(currentPlayer.getAuthToken(), card);

        //if(results.isSuccess()) {
        // TODO This will be added to the player's hand through the Poller
        clientModelRoot.addTrainCardToPlayer(currentPlayer, card);
        //}else{
        //    return results.getErrorInfo();
        //}

        return null;
    }

    public String discardTrainCards(Color color, int numberToDiscard){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();
        if(!currentPlayer.hasSufficientCards(color, numberToDiscard)){
            return "You do not have enough cards to do this!";
        }

        try {
            ArrayList<TrainCard> cardsToDiscard = new ArrayList<>();
            for (int i = 0; i < numberToDiscard; i++) {

                TrainCard card = currentPlayer.getTrainCard(color);
                cardsToDiscard.add(card);
            }

            GameResults results = (GameResults)proxy.DiscardTrainCard(currentPlayer.getAuthToken(), cardsToDiscard);
            if(!results.isSuccess()) {
                return results.getErrorInfo();
            }

            for(int i = 0; i < numberToDiscard; i++){
                currentPlayer.removeTrainCard(color);
            }
        }catch (Exception e){
            return e.getMessage();
        }

        return null;
    }

    public String drawDestinationCards(){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        GameResults results = (GameResults)proxy.DrawDestinationCard(currentPlayer.getAuthToken());

        if(results.isSuccess()) {
            // TODO This will be added to the player's hand through the Poller
            ArrayList<DestinationCard> newCards = new ArrayList<>();
            DestinationCard card1 = new DestinationCard(City.DENVER, City.KANSAS_CITY, 5);
            DestinationCard card2 = new DestinationCard(City.DENVER, City.OKLAHOMA_CITY, 4);
            DestinationCard card3 = new DestinationCard(City.SALT_LAKE_CITY, City.ATLANTA, 9);

            newCards.add(card1);
            newCards.add(card2);
            newCards.add(card3);

            currentPlayer.addDestinationCards(newCards);

            currentPlayer.notifyObservers();
        }else{
            return results.getErrorInfo();
        }

        return null;
    }

    public String discardDestinationCards(ArrayList<DestinationCard> cards){

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
    public String sendMessage(String message){

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        GameResults results = (GameResults)proxy.SendChat(currentPlayer.getName(), message);

        //if(!results.isSuccess()) {
        //    return results.getErrorInfo();
        //}

        return null;
    }

    public ArrayList<String> getAllMessages() { return clientModelRoot.getAllMessages(); }

    // Getters
    public Player getCurrentPlayer(){
        return clientModelRoot.getCurrentPlayer();
    }

    public void setCurrentPlayer(Player player){
        clientModelRoot.setCurrentPlayer(player);
    }

    public GameState getCurrentGame() {
        return clientModelRoot.getCurrentGameState();
    }

    public void setCurrentGame(GameState currentGame) {
        clientModelRoot.setCurrentGameState(currentGame);
    }

    public void addObserverToGameState(Observer o){
        clientModelRoot.addObserverToCurrentGameState(o);
    }

    public void removeObserverFromGameState(Observer o){
        clientModelRoot.removeObserverFromCurrentGameState(o);
    }

    public void addObserverToCurrentPlayer(Observer o){
        clientModelRoot.addObserverToCurrentPlayer(o);
    }

    public void removeObserverFromCurrentPlayer(Observer o){
        clientModelRoot.removeObserverFromCurrentPlayer(o);
    }
}
