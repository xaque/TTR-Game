package cs340.game.client.Presenters;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Presenters.Tasks.StartGameTask;
import cs340.game.client.Views.GameLobbyActivity;
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
        StartGameTask startGameTask = new StartGameTask(this, gameName);
        startGameTask.execute();
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

    private void populatePlayerList(ArrayList<String> players) {
        this.view.clearPlayerList();
        for(int i=0; i<players.size(); i++) {
            this.view.setPlayer(i, players.get(i));
        }
    }

    private void checkPlayers(ArrayList<String> players) {
        if(players.size() < 2){
            view.disableButton();
        } else {
            view.enableButton();
        }
    }

    @Override
    public void update(Observable observable, Object o) {

        view.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(facade.getCurrentGame().isGameStarted()){
                    onStartGameResponse(true);
                }
                populatePlayerList(AppLayerFacade.getInstance().getCurrentGame().getPlayers());
                checkPlayers(AppLayerFacade.getInstance().getCurrentGame().getPlayers());
            }
        });

    }
}