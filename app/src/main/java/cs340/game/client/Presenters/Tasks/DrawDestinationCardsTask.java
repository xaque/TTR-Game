package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DestinationsDialogPresenter;

public class DrawDestinationCardsTask extends AsyncTask<Void, Void, String> {

    private DestinationsDialogPresenter presenter;
    private String result;
    private InGameFacade facade = InGameFacade.getInstance();


    public DrawDestinationCardsTask(DestinationsDialogPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.drawDestinationCards();
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
        // on success presenter will be updated from the the current player notifyObserver()
    }
}
