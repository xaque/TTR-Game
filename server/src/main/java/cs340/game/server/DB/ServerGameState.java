package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.ServerException;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;

public class ServerGameState {
    private GameState gameState;
    private DestinationCardDeck destinationCardDeck;
    private TrainCardDeck trainCardDeck;

    public ServerGameState(String name, List<User> users) {
        //Create Players from Users
        ArrayList<Player> players = new ArrayList<>();
        for(int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            String authToken = users.get(i).getAuthToken();
            Player player = new Player(username, authToken);
            players.add(player);

        }

        //Initialize GameState and decks
        this.gameState = new GameState(name, players);
        this.destinationCardDeck = new DestinationCardDeck();
        this.trainCardDeck = new TrainCardDeck();
        for(Player player : players) {
            List<DestinationCard> drawnDestinationCards = destinationCardDeck.drawCards();
            player.addDestinationCards(drawnDestinationCards);
            String actionMessage = player.getName() + " drew " + Integer.toString(drawnDestinationCards.size()) + " Destination cards.";
            GameHistoryAction action = new GameHistoryAction(actionMessage, null);
            gameState.addHistoryAction(action);

            List<TrainCard> drawnTrainCards = trainCardDeck.drawStartingCards();
            for(TrainCard card : drawnTrainCards) {
                player.addTrainCard(card);
            }
            actionMessage = player.getName() + " drew " + Integer.toString(drawnTrainCards.size()) + " Train cards.";
            action = new GameHistoryAction(actionMessage, null);
            gameState.addHistoryAction(action);
        }

        this.trainCardDeck.initializeFaceUpCards();
        updateDeckSizes();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Player> getPlayers() {
        return this.gameState.getPlayers();
    }

    public List<DestinationCard> drawDestinationCards(String username) {
        List<DestinationCard> drawnCards = this.destinationCardDeck.drawCards();
        List<Player> players = this.gameState.getPlayers();
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equals(username)) {
                players.get(i).addDestinationCards(drawnCards);
                break;
            }
        }
        return drawnCards;
    }

    public void returnDestinationCards(List<DestinationCard> cards, String username) {
        List<Player> players = this.gameState.getPlayers();
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
        List<Player> players = this.gameState.getPlayers();
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
        List<Player> players = this.gameState.getPlayers();
        for(Player player: players) {
            if(player.getName().equals(username)) {
                player.addTrainCard(drawnCard);
                break;
            }
        }
        return drawnCard;
    }

    public void discardTrainCards(List<TrainCard> cardsToDiscard, String username) {
        List<Player> players = this.gameState.getPlayers();
        List<TrainCard> discardedCards = new ArrayList<>();
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

    public void addGameCommand(GameHistoryAction action) {
        this.gameState.addHistoryAction(action);
    }

    public void updateDeckSizes() {
        gameState.setDestinationTicketDeckSize(destinationCardDeck.getSize());
        gameState.setTrainCardDeckSize(trainCardDeck.getSize());
        gameState.setFaceUpCards(trainCardDeck.getFaceUpCards());
    }
}
