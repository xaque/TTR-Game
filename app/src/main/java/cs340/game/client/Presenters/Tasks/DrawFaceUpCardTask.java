package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainsPresenter;
import cs340.game.shared.models.TrainCard;

public class DrawFaceUpCardTask extends AsyncTask<Void, Void, String> {
    private DrawTrainsPresenter presenter;
    private String result;
    private TrainCard drawnCard;
    private int faceUpPosition;
    private InGameFacade facade = InGameFacade.getInstance();


    public DrawFaceUpCardTask(DrawTrainsPresenter presenter, TrainCard card, int index) {
        this.presenter = presenter;
        this.drawnCard = card;
        this.faceUpPosition = index;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.drawFaceUpTrainCard(drawnCard, this.faceUpPosition);
        } catch (Exception e){
            return e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            presenter.onError(result);
        }
    }
}
