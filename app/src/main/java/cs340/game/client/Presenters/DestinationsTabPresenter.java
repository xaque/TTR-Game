package cs340.game.client.Presenters;

import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.DestinationsFragment;

public class DestinationsTabPresenter implements Observer {
    private DestinationsFragment view;
    private InGameFacade facade = InGameFacade.getInstance();

    public DestinationsTabPresenter(DestinationsFragment destinationsFragment) {
        view = destinationsFragment;

        facade.getCurrentPlayer().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        view.getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                view.updateUI();
            }
        });
    }
}
