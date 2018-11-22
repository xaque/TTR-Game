package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainsPresenter;
import cs340.game.shared.models.TrainCard;

public class DrawFaceUpCardTask extends AsyncTask<Void, Void, String> {
    private DrawTrainsPresenter presenter;
    private String result;
    private TrainCard drawnCard;
    private InGameFacade facade = InGameFacade.getInstance();


    public DrawFaceUpCardTask(DrawTrainsPresenter presenter, TrainCard card) {
        this.presenter = presenter;
        this.drawnCard = card;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.drawFaceUpTrainCard(drawnCard);
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
