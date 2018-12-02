package cs340.game.shared.results;

import cs340.game.shared.models.TrainCard;

/**
 * Created by Stephen on 10/31/2018.
 */

public class TrainCardResults implements Results {
    boolean success;
    TrainCard card;
    String errorInfo;

    public TrainCardResults(boolean success, TrainCard card, String errorInfo) {
        this.success = success;
        this.card = card;
        this.errorInfo = errorInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public TrainCard getCard() {
        return card;
    }

    public void setCards(TrainCard card) {
        this.card = card;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorMessage) {
        this.errorInfo = errorInfo;
    }
}
