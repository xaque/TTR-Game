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

    private int trainDeckSize;
    private List<TrainCard> faceUpCards;


    public DrawTrainsPresenter(DrawTrainsFragment drawTrainsFragment) {
        view = drawTrainsFragment;

        gameState = gameFacade.getCurrentGame();
        faceUps = gameState.getFaceUpCards();
        state = new NoCardsDrawn(this);

        trainDeckSize = 0;
        faceUpCards = new ArrayList<>();

        updateCardsLeft(gameState.getTrainCardDeckSize());
        //Get cards left in deck

        setFaceUps(faceUps);

        gameFacade.addObserverToGameState(this);
    }

    public void updateCardsLeft(int cards) {
        view.updateCardsLeft(Integer.toString(cards));
    }

    public void setFaceUps(ArrayList<TrainCard> cards) {
        faceUps = cards;
        for(int i=1; i<=cards.size(); i++) {
            view.setCard(i, cards.get(i-1).getColor());
        }
    }

    public void ok_clicked(int index){
        TrainCard newCard;
        if(index != 5 && index > faceUps.size()-1){
            onError("Not a real card");
        }
        else if(index != 5 && faceUps.size() == 0){
            onError("No face up cards");
        }
        else if(index == 5 && gameFacade.getCurrentGame().getTrainCardDeckSize() == 0){
            onError("No cards in deck");
        }
        else if(index == -1){
            onError("Please select a card");
        }
        else if(index != 5){
            newCard = faceUps.get(index);
            if(newCard.getColor() == Color.WILD){
                state.drawLocomotive(newCard, index);
            } else {
                state.drawFaceUpCard(newCard, index);
            }
        } else {
            state.drawFromDeck();
        }
        view.unselectCards();
        int cardsLeft = gameFacade.getCurrentGame().getTrainCardDeckSize();
        updateCardsLeft(cardsLeft);
    }

    public boolean changedFaceUpCards(List<TrainCard> trainCards) {
        if(trainCards.size() != faceUpCards.size()) {
            return true;
        }
        for(int i = 0; i < faceUpCards.size(); i++) {
            if(trainCards.get(i).getColor() != faceUpCards.get(i).getColor()) {
                return true;
            }
        }
        return false;
    }

    public void back_clicked() {
        state.back();
    }

    public void setState(DrawTrainsState newState){
        this.state = newState;
    }

    public DrawTrainsState getState(){
        return state;
    }

    public void onError(String message) {
        view.onError(message);
    }

    public void closeDialog(){
        view.closeDialog();
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof GameState) {
            final GameState state = (GameState) observable;
            boolean changed = false;
            if(state.getTrainCardDeckSize() != trainDeckSize) {
                trainDeckSize = state.getTrainCardDeckSize();
                changed = true;
            }
            if(changedFaceUpCards(state.getFaceUpCards())) {
                faceUpCards = state.getFaceUpCards();
                changed = true;
            }
            if(changed) {
                view.getTheActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCardsLeft(state.getTrainCardDeckSize());
                        setFaceUps(state.getFaceUpCards());
                    }
                });
            }
        }
    }
}
