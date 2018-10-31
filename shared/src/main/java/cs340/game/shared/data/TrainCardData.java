package cs340.game.shared.data;

import cs340.game.shared.CommandType;
import cs340.game.shared.models.TrainCard;

public class TrainCardData extends Data{
    private String authToken;
    private TrainCard card;

    public TrainCardData(CommandType commandType, String authToken, TrainCard card){
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

    public TrainCard getCard() {
        return card;
    }

    public void setCard(TrainCard card) {
        this.card = card;
    }
}
