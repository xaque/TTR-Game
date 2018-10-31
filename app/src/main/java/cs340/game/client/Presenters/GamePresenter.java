package cs340.game.client.Presenters;

import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.InGameFacade;
import cs340.game.client.Views.GameActivity;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;

public class GamePresenter implements Observer {

    private GameActivity view;
    private AppLayerFacade facade = AppLayerFacade.getInstance();
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private Player currentPlayer;

    private int[] coords = {225, 34, 260, 23, 297, 18, 330, 18, 366, 26, 400, 38};


    public GamePresenter(GameActivity view) {
        this.view = view;
        gameState = gameFacade.getCurrentGame();
        currentPlayer = gameFacade.getCurrentPlayer();

        view.setPlayerName(currentPlayer.getName());
        //view.setPlayers(gameFacade.getCurrentGame().getPlayers());



        gameFacade.addObserver(this);
    }


    //*************************RUN DEMO************************
    public void runDemo(){
        //Update player points
        //.getCurrentGame();
        //Add/remove train cards for player

        //Add/remove player destination cards for player

        //Update number of train cards for opponents

        //Update number of train cars for opponents

        //Update number of destination cards for opponents

        //Update visible cards in the train card deck
        //gameFacade.changeFaceUpCards();
        //Update number of cards in deck
        //gameFacade.drawTrainCard();
        //Update number of cards in destination card deck
        //gameFacade.drawDestinationCard();
        //Add claimed route
        view.placeRoute("yellow", coords);
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
        view.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                view.updatePoints(currentPlayer.getPoints());
                //view.updateTrainsLeft(currentPlayer.getTrainsLeft());
            }
        });

    }
}
