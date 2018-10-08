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
    private AppLayerFacade facade = AppLayerFacade.getInstance();

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
    }

    private LoginTask loginTask;
    private RegisterTask registerTask;

    public void login(String username, String password){
        loginTask = new LoginTask(this, username, password);
        loginTask.execute();
        //facade.Login(this, username, password);
    }

    public void register(String username, String password) {
        registerTask = new RegisterTask(this, username, password);
        registerTask.execute();
        //facade.Register(this, username, password);
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

class LoginTask extends AsyncTask<Void, Void, LoginResults> {

    private MainActivityPresenter presenter;
    private final String username;
    private final String password;
    private ServerProxy serverProxy;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public LoginTask(MainActivityPresenter presenter, String username, String password) {
        this.presenter = presenter;
        this.username = username;
        this.password = password;
    }

    @Override
    protected LoginResults doInBackground(Void... voids) {
        try{
            facade.Login(presenter, username, password);
        } catch (Exception e){
            presenter.onError("There was an error");
            //Log.w(TAG, "Exception while constructing URL" + e.getMessage());
        }
        return null;
    }
}

class RegisterTask extends AsyncTask<Void, Void, LoginResults> {

    private MainActivityPresenter presenter;
    private final String username;
    private final String password;
    private ServerProxy serverProxy;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public RegisterTask(MainActivityPresenter presenter, String username, String password) {
        this.presenter = presenter;
        this.username = username;
        this.password = password;
    }

    @Override
    protected LoginResults doInBackground(Void... voids) {
        try{
            facade.Register(presenter, username, password);
        } catch (Exception e){
            presenter.onError("There was an error");
            //Log.w(TAG, "Exception while constructing URL" + e.getMessage());
        }
        return null;
    }
}