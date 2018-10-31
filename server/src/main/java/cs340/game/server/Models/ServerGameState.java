package cs340.game.server.Models;

import java.util.List;

import cs340.game.server.DB.DestinationCardDeck;
import cs340.game.server.DB.GameCommandLog;
import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.User;

/**
 * Created by Stephen on 10/27/2018.
 */

public class ServerGameState {
    private String name;
    private List<Player> players;
    private DestinationCardDeck destinationCardDeck;
    private GameCommandLog commandLog;

    public ServerGameState(String name, List<User> users) {
        this.name = name;
        for(int i = 0; i < users.size(); i++) {
            String username = users.get(i).getUsername();
            String authToken = users.get(i).getAuthToken();
            Player player = new Player(username, authToken);
            this.players.add(player);
        }
        this.destinationCardDeck = new DestinationCardDeck();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<DestinationCard> drawDestinationCards(String username) {
        List<DestinationCard> drawnCards = this.destinationCardDeck.drawCards();
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).getName().equals(username)) {
                players.get(i).addDestinationCards(drawnCards);
                break;
            }
        }
        return drawnCards;
    }

    public void returnDestinationCards(List<DestinationCard> cards, String username) {
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
}
