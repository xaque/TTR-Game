package cs340.game.client.Presenters;


import java.util.Observable;
import java.util.Observer;

import cs340.game.client.Views.MainActivity;

/**
 * Created by Tyler on 9/24/2018.
 */

public class MainActivityPresenter implements Observer {

    private MainActivity view;
    //private ModelFacade modelFacade;


    public MainActivityPresenter(MainActivity view) {
        this.view = view;
        //model = new User();
    }

    public void login(String username, String password){
        onLoginResponse(true);
    }

    public void register(String username, String password) {

    }

    public void onLoginResponse(boolean isLoginSuccess) {
        view.onLoginResponse(isLoginSuccess);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
