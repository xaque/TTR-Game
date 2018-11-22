package cs340.game.client.Presenters.Tasks;

import android.os.AsyncTask;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Presenters.MainActivityPresenter;

public class LoginTask extends AsyncTask<Void, Void, String> {

    private MainActivityPresenter presenter;
    private final String username;
    private final String password;

    private String result;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public LoginTask(MainActivityPresenter presenter, String username, String password) {
        this.presenter = presenter;
        this.username = username;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.Login(presenter, username, password);
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