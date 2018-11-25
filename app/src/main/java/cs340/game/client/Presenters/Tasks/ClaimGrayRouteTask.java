package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.ClaimRoutePresenter;
import cs340.game.shared.Color;
import cs340.game.shared.models.Route;

public class ClaimGrayRouteTask extends AsyncTask<Void, Void, String> {
    private ClaimRoutePresenter presenter;
    private Route route;
    private Color color;
    private Color player_color;
    private InGameFacade facade = InGameFacade.getInstance();

    public ClaimGrayRouteTask(ClaimRoutePresenter presenter, Route route, Color color, Color player_color) {
        this.route = route;
        this.color = color;
        this.player_color = player_color;
        this.presenter = presenter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result;
        try{
            result = facade.claimGreyRoute(route, color);
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
