package cs340.game.client.Presenters;


import java.util.Observable;
import java.util.Observer;

import cs340.game.client.Presenters.Tasks.LoginTask;
import cs340.game.client.Presenters.Tasks.RegisterTask;
import cs340.game.client.Views.MainActivity;

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