package cs340.game.client.Presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.AppLayerFacade;
import cs340.game.client.InGameFacade;
import cs340.game.client.Views.GameActivity;
import cs340.game.shared.City;
import cs340.game.shared.Color;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.TrainCard;

public class GamePresenter implements Observer {

    private GameActivity view;
    private AppLayerFacade facade = AppLayerFacade.getInstance();
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private Player currentPlayer;
    private List<Player> players;
    private int turn;

    private int[] coords = {225, 34, 260, 23, 297, 18, 330, 18, 366, 26, 400, 38};


    public GamePresenter(GameActivity view) {
        this.view = view;
        gameState = gameFacade.getCurrentGame();
        currentPlayer = gameFacade.getCurrentPlayer();
        players = gameState.getPlayers();

        view.setPlayerName(currentPlayer.getName());
        setPlayers(players);

        turn = 1;


        gameFacade.addObserver(this);
        currentPlayer.addObserver(this);
    }


    //*************************RUN DEMO************************
    public void runDemo(){
        //Update player points
        currentPlayer.addPoints(1);
        for(Player player : players) {
            player.addPoints(3);
        }
        //Add/remove train cards for player
        currentPlayer.addTrainCard(new TrainCard(Color.PINK));
        currentPlayer.addTrainCard(new TrainCard(Color.WILD));
        currentPlayer.addTrainCard(new TrainCard(Color.BLACK));
        //Add/remove player destination cards for player
        List<DestinationCard> d = new ArrayList<DestinationCard>();
        d.add(new DestinationCard(City.SALT_LAKE_CITY, City.CALGARY, 8));
        currentPlayer.addDestinationCards(d);
        //Update number of train cards for opponents
        for(Player player : players) {
            player.addTrainCard(new TrainCard(Color.WILD));
            player.addTrainCard(new TrainCard(Color.BLACK));
        }
        //Update number of train cars for opponents

        //Update number of destination cards for opponents
        for(Player player : players) {
            player.addDestinationCards(d);
        }
        //Update visible cards in the train card deck
        //gameFacade.changeFaceUpCards();
        //Update number of cards in deck
        //gameFacade.drawTrainCard();
        //Update number of cards in destination card deck
        //gameFacade.drawDestinationCard();
        //Add claimed route
        view.placeRoute("yellow", coords);
        //change turn indicator
        nextTurn();
    }




    //************UPDATE UI FUNCTIONS**********************

    public void setPlayers(List<Player> players) {
        for(int i=1; i <= 5; i++) {
            if(i <= players.size()){
                setPlayer(i, players.get(i-1));
            }
            else {
                view.hidePlayer(i);
            }
        }
    }

    public void setPlayer(int playerNumber, Player player){
        String name = player.getName();
        //String color = player.getColor;

        switch(playerNumber) {
            case 1:
                view.setPlayer1(name);
                break;
            case 2:
                view.setPlayer2(name);
                break;
            case 3:
                view.setPlayer3(name);
                break;
            case 4:
                view.setPlayer4(name);
                break;
            case 5:
                view.setPlayer5(name);
                break;
        }
    }

    public void updatePoints(int points){
        view.updatePoints(Integer.toString(points));
    }

    public void updateTrains(int trainsLeft) {
        view.updateTrainsLeft(Integer.toString(trainsLeft));
    }

    public void nextTurn(){
        if( turn < players.size()) {
            turn++;
        } else {
            turn = 1;
        }
        view.changeTurn(turn);
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
                updatePoints(currentPlayer.getPoints());
                //updateTrainsLeft(currentPlayer.getTrainsLeft());
            }
        });

    }
}
