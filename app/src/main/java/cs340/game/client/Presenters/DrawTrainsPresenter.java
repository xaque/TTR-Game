package cs340.game.client.Presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.DrawTrainStates.DrawTrainsState;
import cs340.game.client.Presenters.DrawTrainStates.NoCardsDrawn;
import cs340.game.client.Views.DrawTrainsFragment;
import cs340.game.shared.Color;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.TrainCard;

public class DrawTrainsPresenter implements Observer {

    private DrawTrainsFragment view;
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private ArrayList<TrainCard> faceUps;
    private DrawTrainsState state;


    public DrawTrainsPresenter(DrawTrainsFragment drawTrainsFragment) {
        view = drawTrainsFragment;

        gameState = gameFacade.getCurrentGame();
        faceUps = gameState.getFaceUpCards();
        state = new NoCardsDrawn(this);

        updateCardsLeft(gameState.getTrainCardDeckSize());
        //Get cards left in deck

        setFaceUps(faceUps);

        gameFacade.addObserver(this);
        gameFacade.addObserverToGameState(this);
        gameFacade.addObserverToCurrentPlayer(this);
    }

    public void updateCardsLeft(int cards) {
        view.updateCardsLeft(Integer.toString(cards));
    }

    public void setFaceUps(ArrayList<TrainCard> cards) {
        for(int i=1; i<=cards.size(); i++) {
            view.setCard(i, cards.get(i-1).getColor());
        }
    }

    public void ok_clicked(int index){
        TrainCard newCard;
        if(index != 5){
            newCard = faceUps.get(index);
            if(newCard.getColor() == Color.WILD){
                state.drawLocomotive(newCard);
            } else {
                state.drawFaceUpCard(newCard);
            }
        } else {
            state.drawFromDeck();
        }

    }

    public void back_clicked() {
        state.back();
    }

    public void drawCard(int index) {
        ArrayList<TrainCard> newList = gameState.getFaceUpCards();
        TrainCard newCard;
        newCard = faceUps.get(index);
        gameFacade.drawFaceUpTrainCard(newCard);

        newList.set(index, newCard);
        gameState.setFaceUpCards(newList);
    }

    public void drawFromDeck(){

    }

    public void setState(DrawTrainsState newState){
        this.state = newState;
    }

    public void onError(String message) {
        view.onError(message);
    }

    public void closeDialog(){
        view.closeDialog();
    }

    @Override
    public void update(Observable observable, Object o) {
        view.getTheActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {
                updateCardsLeft(gameState.getTrainCardDeckSize());
                setFaceUps(gameState.getFaceUpCards());
            }
        });
    }
}
