package cs340.game.client.Presenters;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.Tasks.ClaimGrayRouteTask;
import cs340.game.client.Presenters.Tasks.ClaimRouteTask;
import cs340.game.client.Views.ClaimRouteDialog;
import cs340.game.client.Views.GameActivity;
import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.models.Route;

public class ClaimRoutePresenter implements Observer {

    private ClaimRouteDialog view;
    private InGameFacade facade = InGameFacade.getInstance();
    private Activity gameActivity;
    private ArrayList<Route> routes;
    private Route claimedRoute;

    public ClaimRoutePresenter(ClaimRouteDialog claimRouteDialog) {
        view = claimRouteDialog;

        facade.addObserverToCurrentPlayer(this);
        gameActivity = view.getActivity();
    }


    public ArrayList<Route> getClaimableRoutes(){
        routes = new ArrayList<>();

        int[] coords = {327, 174, 363, 173, 400, 172, 436, 171, 471, 171, 507, 170};
        int[] denver_kc1 = {390, 330, 425, 330, 461, 325, 496, 314};
        Route route1 = new Route(City.HELENA, City.DULUTH, Color.PINK, 6, false, coords);
        Route route2 = new Route(City.DENVER, City.KANSAS_CITY, Color.WILD, 4, false, denver_kc1);
        routes.add(route1);
        routes.add(route2);

        //This is what it's supposed to be
        routes = facade.getClaimableRoutes();
        return routes;
    }

    public void claimRoute(int index) {
        claimedRoute = routes.get(index);
        Color routeColor = claimedRoute.getColor();
        GameActivity context = (GameActivity) view.getActivity();

        if(routeColor == Color.WILD){
            //prompt select color
            view.promptSelectColor();
        } else {
            //TODO: set player color
            context.placeRoute(Color.RED, claimedRoute.getCoordinates());

            ClaimRouteTask claimRouteTask = new ClaimRouteTask(this, claimedRoute);
            claimRouteTask.execute();
            view.closeDialog();
        }


    }

    public Color[] getAvailableColors(){
        Color[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.WHITE, Color.YELLOW };
        return colors;
    }

    public void claimGrayRoute(Color color){
        GameActivity context = (GameActivity) view.getActivity();

        //TODO: set player color
        context.placeRoute(Color.RED, claimedRoute.getCoordinates());

        ClaimGrayRouteTask claimGrayRouteTask = new ClaimGrayRouteTask(this, claimedRoute, color);
        claimGrayRouteTask.execute();

        view.closeDialog();
    }

    public void onError(String message){
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
