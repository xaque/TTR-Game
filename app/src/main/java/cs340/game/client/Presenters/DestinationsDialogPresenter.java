package cs340.game.client.Presenters;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.DestinationsDialog;
import cs340.game.client.Views.GameActivity;
import cs340.game.shared.models.DestinationCard;

public class DestinationsDialogPresenter implements Observer {

    private DestinationsDialog view;
    private InGameFacade facade = InGameFacade.getInstance();
    private Activity gameActivity;

    public DestinationsDialogPresenter(DestinationsDialog destinationsFragment) {
        view = destinationsFragment;

        facade.getCurrentPlayer().addObserver(this);
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
        facade.discardDestinationCards(destinationCards);
    }

//    public void drawDestinationCards() {
//        facade.drawDestinationCards();
//    }
}
