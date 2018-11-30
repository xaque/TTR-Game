package cs340.game.client.Presenters;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.Tasks.DiscardDestinationCardsTask;
import cs340.game.client.Presenters.Tasks.DrawDestinationCardsTask;
import cs340.game.client.Views.DestinationsDialog;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Player;

public class DestinationsDialogPresenter implements Observer {

    private DestinationsDialog view;
    private InGameFacade facade = InGameFacade.getInstance();
    private Activity gameActivity;
    private int cardCount;

    public DestinationsDialogPresenter(DestinationsDialog destinationsFragment) {
        view = destinationsFragment;

        facade.addObserverToCurrentPlayer(this);
        gameActivity = view.getActivity();
        cardCount = 0;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Player) {
            Player player = (Player) observable;
            if(player.getDrawnDestinationCards().size() > cardCount) {
                cardCount = player.getDrawnDestinationCards().size();
                view.update(player.getDrawnDestinationCards());
            }
        }
    }

    public void onError(String message) {
        Toast.makeText(gameActivity, message, Toast.LENGTH_LONG).show();
    }

    public ArrayList<DestinationCard> getDestinationCards_StartOfGame() {
        return facade.getDestinationCardsFromCurrentPlayer();
    }

    public List<DestinationCard> getDrawnDestinationCards() {
        return facade.getDrawnDestinationCards();
    }

    public void submitDestinationCardSelection(ArrayList<DestinationCard> selectedCards) {
        discardDestinationCards(selectedCards);
        clearDrawnDestinationCards();
    }

    public void acceptDestinationCards(List<DestinationCard> destinationCards) {
        facade.acceptDestinationCards((ArrayList<DestinationCard>)destinationCards);
    }

    public void discardDestinationCards(ArrayList<DestinationCard> destinationCards) {
        DiscardDestinationCardsTask discardDestinationCardsTask = new DiscardDestinationCardsTask(this, destinationCards);
        discardDestinationCardsTask.execute();
    }

    public void clearDrawnDestinationCards() {
        cardCount = 0;
        facade.clearDrawnDestinationCards();
    }

    public void drawDestinationCards() {
        DrawDestinationCardsTask drawDestinationCardsTask = new DrawDestinationCardsTask(this);
        drawDestinationCardsTask.execute();
    }

    public boolean isStartOfGame() {
        return facade.isStartOfGame();
    }

    public void setStartOfGame(boolean set) {
        facade.setStartOfGame(set);
    }
}


