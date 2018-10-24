package cs340.game.shared.data;

import cs340.game.shared.CommandType;
import cs340.game.shared.models.DestinationCard;

public class DestinationCardData extends Data{

    private CommandType commandType;
    private String authToken;
    private DestinationCard card;

    public DestinationCardData(CommandType commandType, String authToken, DestinationCard card){
        this.commandType = commandType;
        this.authToken = authToken;
        this.card = card;
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

    public DestinationCard getCard() {
        return card;
    }

    public void setCard(DestinationCard card) {
        this.card = card;
    }
}
