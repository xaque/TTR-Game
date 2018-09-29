package cs340.game.client.Presenters;


import cs340.game.client.Views.GameListActivity;

/**
 * Created by Tyler on 9/26/2018.
 */

public class GameListPresenter {

    private GameListActivity view;

    public GameListPresenter(GameListActivity view) {
        this.view = view;
        //model = new User();
    }



    public void createGame(String gameName) {
        onCreateGameResponse(true);
    }

    public void joinGame() {

    }

    public void logout() {

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
}
