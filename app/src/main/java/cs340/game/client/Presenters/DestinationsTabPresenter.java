package cs340.game.client.Presenters;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.DestinationsFragment;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Player;

public class DestinationsTabPresenter implements Observer {
    private DestinationsFragment view;
    private InGameFacade facade = InGameFacade.getInstance();
    private int cardCount;

    public DestinationsTabPresenter(DestinationsFragment destinationsFragment) {
        view = destinationsFragment;
        cardCount = 0;

        facade.getCurrentPlayer().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        ArrayList<DestinationCard> destinationCards;
        if(observable instanceof Player) {
            Player currentPlayer = (Player)observable;
            destinationCards = currentPlayer.getDestinationCards();
        } else {
            destinationCards = InGameFacade.getInstance().getDestinationCardsFromCurrentPlayer();
        }
        if(destinationCards.size() != cardCount) {
            cardCount = destinationCards.size();
            view.update(destinationCards);
        }
    }
}
