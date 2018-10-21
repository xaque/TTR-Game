package cs340.game.client.Presenters;

import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Views.GameActivity;

public class GamePresenter implements Observer {

    private GameActivity view;
    private AppLayerFacade facade = AppLayerFacade.getInstance();

    public GamePresenter(GameActivity view) {
        this.view = view;
    }







    //************UPDATE UI FUNCTIONS**********************

    public void updatePoints(int points){
        view.updatePoints(points);
    }

    public void updateTrains(int trainsLeft) {
        view.updateTrainsLeft(trainsLeft);
    }





    public void leaveGame() {
        //facade.LeaveGame()
        onLeaveGameResponse(true);
    }

    private void onLeaveGameResponse(boolean isLeaveSuccess) {
        view.onLeaveGameResponse(isLeaveSuccess);
    }

    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void update(Observable observable, Object o) {

        //updatePoints(facade.something);
        //updateTrainsLeft(facade.something);
    }
}
