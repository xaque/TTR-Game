package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Presenters.GameListPresenter;

public class JoinGameTask extends AsyncTask<Void, Void, String> {

    private GameListPresenter presenter;
    private final String gameName;
    private String result;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public JoinGameTask(GameListPresenter presenter, String gameName) {
        this.presenter = presenter;
        this.gameName = gameName;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.JoinGame(presenter, gameName);
        } catch (Exception e){
            return e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            presenter.onError(result);
        }
    }
}
