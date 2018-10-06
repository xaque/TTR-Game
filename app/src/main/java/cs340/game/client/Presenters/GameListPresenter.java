package cs340.game.client.Presenters;


import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Views.GameListActivity;

/**
 * Created by Tyler on 9/26/2018.
 */

public class GameListPresenter implements Observer {

    private GameListActivity view;

    private AppLayerFacade facade = AppLayerFacade.getInstance();

    public GameListPresenter(GameListActivity view) {
        this.view = view;
        facade.addObserver(this);
    }


    public void createGame(String gameName) {
        facade.CreateGame(this, gameName);
    }

    public void joinGame(String gameName) {
        facade.JoinGame(this, gameName);
    }

    public void logout() {
        facade.Logout();
    }

    public void onCreateGameResponse(boolean isCreateGameSuccess) {
        facade.deleteObserver(this);
        view.onCreateGameResponse(isCreateGameSuccess);
    }

    public void onJoinGameResponse(boolean isJoinGameSuccess) {
        facade.deleteObserver(this);
        view.onJoinGameResponse(isJoinGameSuccess);
    }

    public void onLogOutResponse(boolean isLogOutSuccess) {
        facade.deleteObserver(this);
        view.onLogOutResponse(isLogOutSuccess);
    }

    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {
        //Update the game list :)
    }
}
