package cs340.game.client.Presenters;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.ClaimRouteDialog;
import cs340.game.client.Views.GameActivity;
import cs340.game.shared.models.Route;

public class ClaimRoutePresenter implements Observer {

    private ClaimRouteDialog view;
    private InGameFacade facade = InGameFacade.getInstance();
    private Activity gameActivity;

    public ClaimRoutePresenter(ClaimRouteDialog claimRouteDialog) {
        view = claimRouteDialog;

        facade.addObserverToCurrentPlayer(this);
        gameActivity = view.getActivity();
    }


    public ArrayList<Route> getClaimableRoutes(){
        return facade.getClaimableRoutes();
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
