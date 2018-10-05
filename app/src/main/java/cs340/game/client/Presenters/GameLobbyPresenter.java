package cs340.game.client.Presenters;


import cs340.game.client.AppLayerFacade;
import cs340.game.client.Views.GameLobbyActivity;

/**
 * Created by Tyler on 9/27/2018.
 */

public class GameLobbyPresenter {

    private GameLobbyActivity view;
    private AppLayerFacade facade;

    public GameLobbyPresenter(GameLobbyActivity view) {
        this.view = view;

        facade = AppLayerFacade.getInstance();

        this.view.setGameName("Ticket To Ride!");

        populatePlayerList();
    }

    public void startGame() {
        onStartGameResponse(true);
    }

    public void onStartGameResponse(boolean isStartGameSuccess) {
        view.onStartGameResponse(isStartGameSuccess);
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

    private void populatePlayerList() {
        this.view.setPlayer1("Tyler");
    }
}
