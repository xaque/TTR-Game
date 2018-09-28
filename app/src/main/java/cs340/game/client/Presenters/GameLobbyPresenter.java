package cs340.game.client.Presenters;


import cs340.game.client.Views.GameLobbyActivity;

/**
 * Created by Tyler on 9/27/2018.
 */

public class GameLobbyPresenter {

    private GameLobbyActivity view;

    public GameLobbyPresenter(GameLobbyActivity view) {
        this.view = view;
        //model = new User();
    }



    public void startGame() {
        onStartGameResponse(true);
    }



    public void onStartGameResponse(boolean isCreateGameSuccess) {
        view.onStartGameResponse(isCreateGameSuccess);
    }
}
