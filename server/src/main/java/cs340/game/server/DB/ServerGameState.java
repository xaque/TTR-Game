package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.RoutePointValues;
import cs340.game.shared.ServerException;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;

public class ServerGameState {
    private GameState gameState;
    private DestinationCardDeck destinationCardDeck;
    private TrainCardDeck trainCardDeck;
    private GameRoutesDatabase gameRoutesDatabase;

    public ServerGameState(String name, ArrayList<User> users) {
        //Create Players from Users
        ArrayList<Player> players = new ArrayList<>();
        // the order of users list has been randomized in StartGameCommand to create a turn order
        for(int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            String authToken = users.get(i).getAuthToken();
            Player player = new Player(username, authToken);
            players.add(player);

        }

        //Initialize GameState, decks, and unclaimed routes
        this.gameState = new GameState(name, players);
        this.destinationCardDeck = new DestinationCardDeck();
        this.trainCardDeck = new TrainCardDeck();
        this.gameRoutesDatabase = new GameRoutesDatabase();

        for(Player player : players) {
            ArrayList<DestinationCard> drawnDestinationCards = destinationCardDeck.drawCards();
            player.addDestinationCards(drawnDestinationCards);
            String actionMessage = player.getName() + " drew " + Integer.toString(drawnDestinationCards.size()) + " Destination cards.";
            GameHistoryAction action = new GameHistoryAction(actionMessage, null);
            gameState.addHistoryAction(action);

            ArrayList<TrainCard> drawnTrainCards = trainCardDeck.drawStartingCards();
            for(TrainCard card : drawnTrainCards) {
                player.addTrainCard(card);
            }
            actionMessage = player.getName() + " drew " + Integer.toString(drawnTrainCards.size()) + " Train cards.";
            action = new GameHistoryAction(actionMessage, null);
            gameState.addHistoryAction(action);
        }

        this.trainCardDeck.resetFaceUpCards();
        this.trainCardDeck.checkFaceUpLocomotives();
        updateDeckSizes();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Player> getPlayers() {
        return this.gameState.getPlayers();
    }

    public ArrayList<DestinationCard> drawDestinationCards(String username) {
        ArrayList<DestinationCard> drawnCards = this.destinationCardDeck.drawCards();
        ArrayList<Player> players = this.gameState.getPlayers();
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equals(username)) {
                players.get(i).addDestinationCards(drawnCards);
                break;
            }
        }
        return drawnCards;
    }

    public void returnDestinationCards(ArrayList<DestinationCard> cards, String username) {
        ArrayList<Player> players = this.gameState.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(username)) {
                players.get(i).removeDestinationCards(cards);
                break;
            }
        }
        this.destinationCardDeck.returnCards(cards);
    }

    public TrainCard drawTrainCardFromDeck(String username) {
        TrainCard drawnCard = this.trainCardDeck.drawCardFromDeck();
        ArrayList<Player> players = this.gameState.getPlayers();
        for(Player player: players) {
            if(player.getName().equals(username)) {
                player.addTrainCard(drawnCard);
                break;
            }
        }
        return drawnCard;
    }

    public TrainCard drawTrainCardFaceUp(int position, String username) {
        TrainCard drawnCard = this.trainCardDeck.drawFaceUpCard(position);
        ArrayList<Player> players = this.gameState.getPlayers();
        for(Player player: players) {
            if(player.getName().equals(username)) {
                player.addTrainCard(drawnCard);
                break;
            }
        }
        return drawnCard;
    }

    public boolean tooManyLocomotives() {
        return this.trainCardDeck.checkFaceUpLocomotives();
    }

    public void discardTrainCards(ArrayList<TrainCard> cardsToDiscard, String username) {
        ArrayList<Player> players = this.gameState.getPlayers();
        ArrayList<TrainCard> discardedCards = new ArrayList<>();
        try {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getName().equals(username)) {
                    for (int j = 0; j < cardsToDiscard.size(); j++) {
                        players.get(i).removeTrainCard(cardsToDiscard.get(j).getColor());
                        discardedCards.add(cardsToDiscard.get(j));
                    }
                    break;
                }
            }
            this.trainCardDeck.discardCards(discardedCards);
        }
        catch(Exception ex) {
            ServerException exception = new ServerException(ex.getMessage());
            return;
        }
    }

    public void checkFaceUpLocomotives() {

    }

    public void claimRoute(Route routeToClaim, String username) throws ServerException{
        gameRoutesDatabase.claimRoute(routeToClaim, username);
        ArrayList<Player> players = this.gameState.getPlayers();
        for (Player player : players) {
            if (player.getName().equals(username)) {
                int points = RoutePointValues.getInstance().getPointValue(routeToClaim.getLength());
                player.addPoints(points);
                player.subtractTrainTokens(routeToClaim.getLength());
                break;
            }
        }
    }

    public void addGameCommand(GameHistoryAction action) {
        this.gameState.addHistoryAction(action);
    }

    public void updateDeckSizes() {
        gameState.setDestinationTicketDeckSize(destinationCardDeck.getSize());
        gameState.setTrainCardDeckSize(trainCardDeck.getSize());
        gameState.setFaceUpCards(trainCardDeck.getFaceUpCards());
    }

    public void endTurn() {
        Player playerEndingTurn = gameState.getPlayerByName(gameState.getCurrentTurnPlayer());
        if(playerEndingTurn.getTrainTokens() <= 2 && !gameState.isFinalRound()) {
            gameState.setFinalRound(true);
            gameState.setLastPlayerInFinalRound(gameState.getCurrentTurnPlayer());

            String actionMessage = "The final round has begun! Last player will be " + gameState.getCurrentTurnPlayer();
            GameHistoryAction action = new GameHistoryAction(actionMessage, null);
            addGameCommand(action);
        }
        else if(gameState.isFinalRound() && gameState.getLastPlayerInFinalRound().equals(gameState.getCurrentTurnPlayer())) {
            //TODO add code to change state of the game to done so that the ending screen can be displayed
            gameState.setGameOver(true);
        }
        gameState.setOneTrainCardDrawn(false);
        gameState.nextPlayerTurn();
    }
}
