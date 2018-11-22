package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import java.util.ArrayList;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DestinationsDialogPresenter;
import cs340.game.shared.models.DestinationCard;

public class DiscardDestinationCardsTask extends AsyncTask<Void, Void, String> {

    private DestinationsDialogPresenter presenter;
    private final ArrayList<DestinationCard> discardedDestinationCards;
    private String result;
    private InGameFacade facade = InGameFacade.getInstance();


    public DiscardDestinationCardsTask(DestinationsDialogPresenter presenter, ArrayList<DestinationCard> discardedDestinationCards) {
        this.presenter = presenter;
        this.discardedDestinationCards = discardedDestinationCards;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.discardDestinationCards(discardedDestinationCards);
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
