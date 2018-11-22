package cs340.game.client.Presenters.DrawTrainStates;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainsPresenter;
import cs340.game.client.Presenters.Tasks.DrawFaceUpCardTask;
import cs340.game.client.Presenters.Tasks.DrawFromDeckTask;
import cs340.game.shared.models.TrainCard;

public class OneCardDrawn implements DrawTrainsState {

    private DrawTrainsPresenter context;
    private InGameFacade facade = InGameFacade.getInstance();

    public OneCardDrawn(DrawTrainsPresenter context){
        this.context = context;
    }


    @Override
    public void drawFaceUpCard(TrainCard card) {
        DrawFaceUpCardTask drawFaceUpCardTask = new DrawFaceUpCardTask(context, card);
        drawFaceUpCardTask.execute();
        context.closeDialog();
    }

    @Override
    public void drawFromDeck() {
        DrawFromDeckTask drawFromDeckTask = new DrawFromDeckTask(context);
        drawFromDeckTask.execute();
        context.closeDialog();
    }

    @Override
    public void drawLocomotive(TrainCard locomotive) {
        context.onError("You can't pick a locomotive if you've drawn a card already!");
    }

    @Override
    public void back() {
        context.onError("You should draw another card!");
    }
}
