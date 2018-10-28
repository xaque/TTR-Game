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

    public List<DestinationCard> drawDestinationCards() {
        List<DestinationCard> drawnCards = this.destinationCardDeck.drawCards();
        return drawnCards;
    }

    public void addGameCommand(GameHistoryAction action) {
        this.commandLog.addGameCommand(action);
    }

    public GameCommandLog getCommandLog() {
        return this.commandLog;
    }
}
