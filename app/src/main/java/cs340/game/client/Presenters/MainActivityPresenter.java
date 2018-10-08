package cs340.game.client.Presenters;


import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.ServerProxy;
import cs340.game.client.Views.MainActivity;
import cs340.game.shared.LoginResults;

/**
 * Created by Tyler on 9/24/2018.
 */

public class MainActivityPresenter implements Observer {

    private MainActivity view;

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
    }

    public void login(String username, String password){
        LoginTask loginTask = new LoginTask(this, username, password);
        loginTask.execute();
    }

    public void register(String username, String password) {
        RegisterTask registerTask = new RegisterTask(this, username, password);
        registerTask.execute();
    }

    public void onLoginResponse(boolean isLoginSuccess) {
        view.onLoginResponse(isLoginSuccess);
    }

    public void onRegisterResponse(boolean isRegisterSuccess) {
        view.onRegisterResponse(isRegisterSuccess);
    }

    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

class LoginTask extends AsyncTask<Void, Void, String> {

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

class RegisterTask extends AsyncTask<Void, Void, String> {

    private MainActivityPresenter presenter;
    private final String username;
    private final String password;

    private String result;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public RegisterTask(MainActivityPresenter presenter, String username, String password) {
        this.presenter = presenter;
        this.username = username;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.Register(presenter, username, password);
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