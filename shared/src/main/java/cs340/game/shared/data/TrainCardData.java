package cs340.game.shared.data;

import java.util.List;

import cs340.game.shared.CommandType;
import cs340.game.shared.models.TrainCard;

public class TrainCardData extends Data{
    private String authToken;
    private List<TrainCard> cards;

    public TrainCardData(CommandType commandType, String authToken, List<TrainCard> cards){
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

    public List<TrainCard> getCards() {
        return cards;
    }

    public void setCard(List<TrainCard> cards) {
        this.cards = cards;
    }
}
