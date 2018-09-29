package cs340.game.client.Presenters;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.Views.GameActivity;

public class GamePresenter {

    private GameActivity view;
    private AppLayerFacade appLayerFacade = AppLayerFacade.getInstance();

    public GamePresenter(GameActivity view) {
        this.view = view;
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
}
