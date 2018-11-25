package cs340.game.client.Presenters.DrawTrainStates;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainsPresenter;
import cs340.game.client.Presenters.Tasks.DrawFaceUpCardTask;
import cs340.game.client.Presenters.Tasks.DrawFromDeckTask;
import cs340.game.shared.models.TrainCard;

public class NoCardsDrawn implements DrawTrainsState {

    private DrawTrainsPresenter context;
    private InGameFacade facade = InGameFacade.getInstance();

    public NoCardsDrawn(DrawTrainsPresenter context){
        this.context = context;
    }

    @Override
    public void drawFaceUpCard(TrainCard newCard, int index) {
        DrawFaceUpCardTask drawFaceUpCardTask = new DrawFaceUpCardTask(context, newCard, index);
        drawFaceUpCardTask.execute();
        context.setState(new OneCardDrawn(context));
    }

    @Override
    public void drawFromDeck() {
        DrawFromDeckTask drawFromDeckTask = new DrawFromDeckTask(context);
        drawFromDeckTask.execute();
        context.setState(new OneCardDrawn(context));
    }

    @Override
    public void drawLocomotive(TrainCard locomotive, int index) {
        DrawFaceUpCardTask drawFaceUpCardTask = new DrawFaceUpCardTask(context, locomotive, index);
        drawFaceUpCardTask.execute();
        context.closeDialog();
    }

    @Override
    public void back() {
        context.closeDialog();
    }
}
