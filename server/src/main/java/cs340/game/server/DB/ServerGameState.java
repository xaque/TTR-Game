package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.User;

public class ServerGameState {
    private GameState gameState;
    private DestinationCardDeck destinationCardDeck;
    private GameCommandLog commandLog;

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
        this.commandLog = new GameCommandLog();
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

    public void addGameCommand(GameHistoryAction action) {
        this.commandLog.addGameCommand(action);
    }

    public GameCommandLog getCommandLog() {
        return this.commandLog;
    }

    public void updateDeckSizes(){
        //TODO Update gameState deck sizes to be equal to the actual size of the decks
    }
}
