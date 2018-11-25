package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainsPresenter;

public class DrawFromDeckTask extends AsyncTask<Void, Void, String> {

    private DrawTrainsPresenter presenter;
    private String result;
    private InGameFacade facade = InGameFacade.getInstance();


    public DrawFromDeckTask(DrawTrainsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.drawTrainCardFromDeck();
        } catch (Exception e){
            return e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            presenter.onError(result);
        } else {
            presenter.getState().drawFromDeckSuccess();
        }
    }
}
