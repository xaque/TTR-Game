package cs340.game.client.Presenters;


import android.os.AsyncTask;

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

    private CreateGameTask createGameTask;

    public GameListPresenter(GameListActivity view) {
        this.view = view;
        facade.addObserver(this);
    }


    public void createGame(String gameName) {
        createGameTask = new CreateGameTask(this, gameName);
        createGameTask.execute();
        //facade.CreateGame(this, gameName);
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

class CreateGameTask extends AsyncTask<Void, Void, Void> {

    private GameListPresenter presenter;
    private final String gameName;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public CreateGameTask(GameListPresenter presenter, String gameName) {
        this.presenter = presenter;
        this.gameName = gameName;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            facade.CreateGame(presenter, gameName);
        } catch (Exception e){
            presenter.onError("There was an error");
            //Log.w(TAG, "Exception while constructing URL" + e.getMessage());
        }
        return null;
    }
}
