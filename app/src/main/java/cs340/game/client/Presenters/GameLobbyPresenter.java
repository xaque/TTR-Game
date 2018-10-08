package cs340.game.client.Presenters;


import android.os.AsyncTask;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.ServerProxy;
import cs340.game.client.Views.GameLobbyActivity;
import cs340.game.shared.LoginResults;
import cs340.game.shared.models.Game;

/**
 * Created by Tyler on 9/27/2018.
 */

public class GameLobbyPresenter implements Observer {

    private GameLobbyActivity view;
    private AppLayerFacade facade;
    private Game currentGame;

    public GameLobbyPresenter(GameLobbyActivity view) {
        this.view = view;

        facade = AppLayerFacade.getInstance();
        currentGame = facade.getCurrentGame();

        this.view.setGameName(currentGame.getName());

        populatePlayerList(currentGame.getPlayers());
        checkPlayers(currentGame.getPlayers());

        facade.addObserver(this);
    }

    public void startGame(String gameName) {
        facade.StartGame(this, gameName);
    }

    public void onStartGameResponse(boolean isStartGameSuccess) {
        facade.deleteObserver(this);
        view.onStartGameResponse(isStartGameSuccess);
    }

    public void leaveGame() {
        //facade.LeaveGame()
        onLeaveGameResponse(true);
    }

    private void onLeaveGameResponse(boolean isLeaveSuccess) {
        facade.deleteObserver(this);
        view.onLeaveGameResponse(isLeaveSuccess);
    }

    public void onError(String message) {
        view.onError(message);
    }

    private void populatePlayerList(List<String> players) {
        this.view.clearPlayerList();
        for(int i=0; i<players.size(); i++) {
            this.view.setPlayer(i, players.get(i));
        }
    }

    private void checkPlayers(List<String> players) {
        if(players.size() < 2){
            view.disableButton();
        } else {
            view.enableButton();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        populatePlayerList(this.currentGame.getPlayers());
        checkPlayers(this.currentGame.getPlayers());
    }
}


class StartGameTask extends AsyncTask<Void, Void, Void> {

    private GameLobbyPresenter presenter;
    private final String username;
    private final String password;
    private AppLayerFacade facade = AppLayerFacade.getInstance();


    public StartGameTask(GameLobbyPresenter presenter, String username, String password) {
        this.presenter = presenter;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            //facade.Register(presenter, username, password);
        } catch (Exception e){
            presenter.onError("There was an error");
            //Log.w(TAG, "Exception while constructing URL" + e.getMessage());
        }
        return null;
    }
}