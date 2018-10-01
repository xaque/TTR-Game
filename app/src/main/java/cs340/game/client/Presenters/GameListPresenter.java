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
        //model = new User();
    }



    public void createGame(String gameName) {
        //facade.CreateGame(gameName);
        onCreateGameResponse(true);
    }

    public void joinGame() {

    }

    public void logout() {
        //facade.Logout();
        onLogOutResponse(true);
    }


    public void onCreateGameResponse(boolean isCreateGameSuccess) {
        view.onCreateGameResponse(isCreateGameSuccess);
    }

    public void onJoinGameResponse(boolean isJoinGameSuccess) {

    }

    public void onLogOutResponse(boolean isLogOutSuccess) {
        view.onLogOutResponse(isLogOutSuccess);
    }

    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
