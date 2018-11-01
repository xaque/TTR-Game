package cs340.game.shared.data;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.CommandType;
import cs340.game.shared.models.DestinationCard;

public class DestinationCardData extends Data{
    private String authToken;
    private ArrayList<DestinationCard> cards; // should be null to draw, should be list to return to deck

    public DestinationCardData(CommandType commandType, String authToken, ArrayList<DestinationCard> cards){
        this.commandType = commandType;
        this.authToken = authToken;
        this.cards = cards;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public ArrayList<DestinationCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<DestinationCard> cards) {
        this.cards = cards;
    }
}
