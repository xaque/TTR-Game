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

    public GameListPresenter(GameListActivity view) {
        this.view = view;
        facade.addObserver(this);
    }


    public void createGame(String gameName) {
        CreateGameTask createGameTask = new CreateGameTask(this, gameName);
        createGameTask.execute();
    }

    public void joinGame(String gameName) {
        JoinGameTask joinGameTask = new JoinGameTask(this, gameName);
        joinGameTask.execute();
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
        System.out.println("update()");
        view.updateUI();
    }
}

class CreateGameTask extends AsyncTask<Void, Void, String> {

    private GameListPresenter presenter;
    private final String gameName;
    private String result;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public CreateGameTask(GameListPresenter presenter, String gameName) {
        this.presenter = presenter;
        this.gameName = gameName;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.CreateGame(presenter, gameName);
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

class JoinGameTask extends AsyncTask<Void, Void, String> {

    private GameListPresenter presenter;
    private final String gameName;
    private String result;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public JoinGameTask(GameListPresenter presenter, String gameName) {
        this.presenter = presenter;
        this.gameName = gameName;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try{
            result = facade.JoinGame(presenter, gameName);
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
