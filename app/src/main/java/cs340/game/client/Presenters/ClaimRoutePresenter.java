package cs340.game.client.Presenters;

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
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;

public class ClaimRoutePresenter implements Observer {

    private ClaimRouteDialog view;
    private InGameFacade facade = InGameFacade.getInstance();
    private ArrayList<Route> routes;
    private ArrayList<Route> filteredRoutes;
    private Route claimedRoute;

    public ClaimRoutePresenter(ClaimRouteDialog claimRouteDialog) {
        view = claimRouteDialog;

        facade.addObserverToCurrentPlayer(this);
    }


    public ArrayList<Route> getClaimableRoutes(){
        routes = new ArrayList<>();

        /*int[] coords = {327, 174, 363, 173, 400, 172, 436, 171, 471, 171, 507, 170};
        int[] denver_kc1 = {390, 330, 425, 330, 461, 325, 496, 314};
        Route route1 = new Route(City.HELENA, City.DULUTH, Color.PINK, 1, false, coords);
        Route route2 = new Route(City.DENVER, City.KANSAS_CITY, Color.WILD, 1, false, denver_kc1);
        routes.add(route1);
        routes.add(route2);*/

        //This is what it's supposed to be
        routes = facade.getClaimableRoutes();
        filteredRoutes = routes;
        System.out.println(routes.size());
        return routes;
    }

    public void claimRoute(int index) {
        claimedRoute = routes.get(index);
        Color routeColor = claimedRoute.getColor();

        if(routeColor == Color.WILD){
            //prompt select color
            //This will then trigger the claimGrayRoute function
            view.promptSelectColor();
        } else {
            Color player_color = getPlayerColor();
            ClaimRouteTask claimRouteTask = new ClaimRouteTask(this, claimedRoute, player_color);
            claimRouteTask.execute();
        }
    }

    private Color getPlayerColor(){
        ArrayList<Player> players = facade.getCurrentGame().getPlayers();
        int num = players.indexOf(facade.getCurrentPlayer());
        Color[] colors = {Color.RED, Color.YELLOW, Color.BLACK, Color.GREEN, Color.BLUE};
        return colors[num];
    }

    public Color[] getAvailableColors(){
        //Todo: It would be nice if I could get this from a facade call
        Color[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.WHITE, Color.YELLOW };
        return colors;
    }

    public void claimGrayRoute(Color color){
        Color player_color = getPlayerColor();
        ClaimGrayRouteTask claimGrayRouteTask = new ClaimGrayRouteTask(this, claimedRoute, color, player_color);
        claimGrayRouteTask.execute();
    }

    public void placeRoute(Color player_color, int[] route){
        GameActivity context = (GameActivity) view.getActivity();
        assert context != null;
        context.placeRoute(player_color, route);
    }

    public void setFilteredRoutes(ArrayList<Route> filteredRoutes){
        this.filteredRoutes = filteredRoutes;
    }

    public void closeDialog(){
        view.closeDialog();
    }

    public void onError(String message){
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
