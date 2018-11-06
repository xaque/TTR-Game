package cs340.game.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.CommonData;
import cs340.game.shared.data.GamePollerData;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;
import cs340.game.shared.results.ClaimRouteResults;
import cs340.game.shared.results.GamePollerResults;
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
        GameState gameState = getCurrentGame();
        gameState.setOneTrainCardDrawn(!gameState.isOneTrainCardDrawn());

        return null;
    }

    public String drawFaceUpTrainCard(TrainCard card){

        GameState gameState = getCurrentGame();
        if(card.getColor() == Color.WILD){
            if(gameState.isOneTrainCardDrawn()){
                return "You cannot draw a locomotive because you already drew a card, select another train card to draw!";
            }
        }

        Player currentPlayer = clientModelRoot.getCurrentPlayer();

        //GameResults results = (GameResults)proxy.DrawFaceUpTrainCard(currentPlayer.getAuthToken(), card);

        //if(results.isSuccess()) {
        // TODO This will be added to the player's hand through the Poller
        clientModelRoot.addTrainCardToPlayer(currentPlayer, card);
        //}else{
        //    return results.getErrorInfo();
        //}
        if(card.getColor() != Color.WILD){
            gameState.setOneTrainCardDrawn(!gameState.isOneTrainCardDrawn());
        }

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

    public ArrayList<DestinationCard> getDestinationCardsFromCurrentPlayer() {
        return getCurrentPlayer().getDestinationCards();
    }

    public String drawDestinationCards(){

        GameState gameState = getCurrentGame();
        if(gameState.isOneTrainCardDrawn()){
            return "You cannot draw a destination card because you have already drawn a train card, select another train card to draw!";
        }

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

        System.out.println("AUTH TOKEN" + currentPlayer.getAuthToken());
        GameResults results = (GameResults)proxy.DiscardDestinationCards(/*currentPlayer.getName()*/currentPlayer.getAuthToken(), cards);

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

    // ROUTES
    public String claimRoute(Route route){

        GameState gameState = getCurrentGame();
        if(gameState.isOneTrainCardDrawn()){
            return "You cannot claim a route because you have already drawn a train card, select another train card to draw!";
        }

        if(!canClaimRoute(route)){
            return "You cannot claim this route!";
        }

        Player currentPlayer = getCurrentPlayer();
        if(!currentPlayer.hasSufficientCards(route.getColor(), route.getLength())){
            return "You do not have enough cards to claim this route!";
        }

        ClaimRouteResults results = (ClaimRouteResults)proxy.ClaimRoute(currentPlayer.getName(), route);

        return null;
    }

    public String claimGreyRoute(Route route, Color color){

        if(!canClaimRoute(route)){
            return "You cannot claim this route!";
        }

        Player currentPlayer = getCurrentPlayer();
        if(!currentPlayer.hasSufficientCards(color, route.getLength())){
            return "You do not have enough cards of this color to claim this route!";
        }

        ClaimRouteResults results = (ClaimRouteResults)proxy.ClaimGreyRoute(currentPlayer.getName(), route, color);

        return null;
    }

    private boolean canClaimRoute(Route route){

        GameState gameState = getCurrentGame();
        List<Route> routes = gameState.getRoutes();
        int routeCount = 0;
        boolean secondRouteClaimed = false;
        String playerThatClaimedRoute = "";
        for(int i = 0; i < routes.size(); i++){
            if(route.equals(routes.get(i))){
                routeCount++;
                secondRouteClaimed = (secondRouteClaimed || routes.get(i).isClaimed());
                if(secondRouteClaimed){
                    playerThatClaimedRoute = routes.get(i).getPlayerOnRoute();
                }
            }
        }

        if(routeCount > 1) {
            if ((secondRouteClaimed && (gameState.getPlayers().size() < 4))
                    || getCurrentPlayer().getName().equals(playerThatClaimedRoute)) {
                return false;
            }
        }

        return true;
    }

    public boolean isCurrentPlayersTurn(){
        String currentPlayerName = getCurrentPlayer().getName();
        String playerWithCurrentTurn = getCurrentGame().getCurrentTurn();
        return currentPlayerName.equals(playerWithCurrentTurn);
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

    public void preloadGameState(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Player currentPlayer = clientModelRoot.getCurrentPlayer();
                GamePollerData pollerData = new GamePollerData(0, currentPlayer.getName());

                ClientCommunicator communicator = ClientCommunicator.getInstance();
                GamePollerResults results = (GamePollerResults)communicator.send(CommonData.POLLER_URI, pollerData);

                if(results.isSuccess()) {
                    System.out.println("Success");
                    GameState newState = results.getData();

                    clientModelRoot.updateGameState(newState);
                    // The most recent sequence number passed from the server
                    int newSequenceNumber = results.getSequenceNumber();
                    clientModelRoot.setGameSequenceNumber(newSequenceNumber);
                }else{
                    System.out.println("Not Success");
                }
            }
        });
        thread.start();
        while(thread.isAlive()){
            //wait
        }
    }
}
