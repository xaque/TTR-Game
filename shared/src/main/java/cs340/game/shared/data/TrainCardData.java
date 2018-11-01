package cs340.game.shared.data;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.CommandType;
import cs340.game.shared.models.TrainCard;

public class TrainCardData extends Data{
    private String authToken;
    private ArrayList<TrainCard> cards;
    private Integer faceUpPosition;

    public TrainCardData(CommandType commandType, String authToken, ArrayList<TrainCard> cards, Integer faceUpPosition){
        this.commandType = commandType;
        this.authToken = authToken;
        this.cards = cards;
        this.faceUpPosition = faceUpPosition;
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

    public ArrayList<TrainCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<TrainCard> cards) {
        this.cards = cards;
    }

    public int getFaceUpPosition() {
        return faceUpPosition;
    }

    public void setFaceUpPosition(int faceUpPosition) {
        this.faceUpPosition = faceUpPosition;
    }
}
