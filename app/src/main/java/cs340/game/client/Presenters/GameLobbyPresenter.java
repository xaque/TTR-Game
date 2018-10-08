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
    private StartGameTask startGameTask;

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
        startGameTask = new StartGameTask(this, gameName);
        startGameTask.execute();
        //facade.StartGame(this, gameName);
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

    private final String gameName;
    private GameLobbyPresenter presenter;
    private AppLayerFacade facade = AppLayerFacade.getInstance();

    public StartGameTask(GameLobbyPresenter presenter, String gameName) {
        this.presenter = presenter;
        this.gameName = gameName;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            facade.StartGame(presenter, gameName);
        } catch (Exception e){
            presenter.onError(e.toString());
        }
        return null;
    }
}