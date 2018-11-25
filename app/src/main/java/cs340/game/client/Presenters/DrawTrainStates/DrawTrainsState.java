package cs340.game.client.Presenters.DrawTrainStates;

import cs340.game.client.Presenters.DrawTrainsPresenter;
import cs340.game.shared.models.TrainCard;

public interface DrawTrainsState {

    public void drawFaceUpCard(TrainCard card, int index);
    public void drawFromDeck();
    public void drawLocomotive(TrainCard locomotive, int index);
    public void back();

}
