package cs340.game.client.Presenters;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.Tasks.DiscardDestinationCardsTask;
import cs340.game.client.Views.DestinationsDialog;
import cs340.game.shared.models.DestinationCard;

public class DestinationsDialogPresenter implements Observer {

    private DestinationsDialog view;
    private InGameFacade facade = InGameFacade.getInstance();
    private Activity gameActivity;

    public DestinationsDialogPresenter(DestinationsDialog destinationsFragment) {
        view = destinationsFragment;

        facade.addObserverToCurrentPlayer(this);
        gameActivity = view.getActivity();
    }

    @Override
    public void update(Observable observable, Object o) {
        gameActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                view.updateUI();
            }
        });
    }

    public void onError(String message) {
        Toast.makeText(gameActivity, message, Toast.LENGTH_LONG).show();
    }

    public ArrayList<DestinationCard> getDestinationCards_StartOfGame() {
        return facade.getDestinationCardsFromCurrentPlayer();
    }

//    public List<DestinationCard> getDrawnDestinationCards() {
//        return facade.getDrawnDestinationCards();
//    }

//    public void acceptDestinationCards(List<DestinationCard> destinationCards) {
//        facade.acceptDestinationCards(destinationCards);
//    }

    public void discardDestinationCards(ArrayList<DestinationCard> destinationCards) {
        DiscardDestinationCardsTask discardDestinationCardsTask = new DiscardDestinationCardsTask(this, destinationCards);
        discardDestinationCardsTask.execute();
    }

//    public void drawDestinationCards() {
//        facade.drawDestinationCards();
//    }
}


