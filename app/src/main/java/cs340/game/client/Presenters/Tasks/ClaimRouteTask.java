package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.ClaimRoutePresenter;
import cs340.game.client.Views.ClaimRouteDialog;
import cs340.game.shared.Color;
import cs340.game.shared.models.Route;

public class ClaimRouteTask extends AsyncTask<Void, Void, String> {
    private Route route;
    private InGameFacade facade = InGameFacade.getInstance();
    private Color player_color;
    private ClaimRoutePresenter presenter;

    public ClaimRouteTask(ClaimRoutePresenter presenter, Route route, Color player_color) {
        this.presenter = presenter;
        this.route = route;
        this.player_color = player_color;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result;
        try{
            result = facade.claimRoute(route);
        } catch (Exception e){
            return e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            presenter.onError(result);
        } else {
            presenter.placeRoute(player_color, route.getCoordinates());
            presenter.closeDialog();
        }
    }
}
