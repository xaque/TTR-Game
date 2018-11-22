package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Presenters.GameLobbyPresenter;

public class StartGameTask extends AsyncTask<Void, Void, String> {

    private final String gameName;
    private String result;
    private GameLobbyPresenter presenter;
    private AppLayerFacade facade = AppLayerFacade.getInstance();

    public StartGameTask(GameLobbyPresenter presenter, String gameName) {
        this.presenter = presenter;
        this.gameName = gameName;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.StartGame(presenter, gameName);
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