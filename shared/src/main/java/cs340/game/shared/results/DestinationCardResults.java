package cs340.game.shared.results;

import java.util.ArrayList;

import cs340.game.shared.models.DestinationCard;

/**
 * Created by Stephen on 10/28/2018.
 */

public class DestinationCardResults implements Results{
    boolean success;
    ArrayList<DestinationCard> cards;
    String errorInfo;

    public DestinationCardResults(boolean success, ArrayList<DestinationCard> cards, String errorInfo) {
        this.success = success;
        this.cards = cards;
        this.errorInfo = errorInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DestinationCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<DestinationCard> cards) {
        this.cards = cards;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorMessage) {
        this.errorInfo = errorInfo;
    }
}
