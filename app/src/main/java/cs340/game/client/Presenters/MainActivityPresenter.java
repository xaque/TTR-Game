package cs340.game.client.Presenters;


import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Views.MainActivity;
import cs340.game.shared.models.GameList;

/**
 * Created by Tyler on 9/24/2018.
 */

public class MainActivityPresenter implements Observer {

    private MainActivity view;
    private AppLayerFacade appLayerFacade = AppLayerFacade.getInstance();

    public MainActivityPresenter(MainActivity view) {
        this.view = view;
    }

    public void login(String username, String password){
        //appLayerFacade.Login(this, username, password);

        onLoginResponse(true);
    }

    public void register(String username, String password) {
        //appLayerFacade.Register(username, password);
        onError("Couldn't register. Sorry about that.");
        //onRegisterResponse(true);
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
