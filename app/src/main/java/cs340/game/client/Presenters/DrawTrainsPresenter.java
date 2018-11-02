package cs340.game.client.Presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.DrawTrainsFragment;
import cs340.game.shared.Color;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.TrainCard;

public class DrawTrainsPresenter implements Observer {

    private DrawTrainsFragment view;
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private ArrayList<TrainCard> faceUps;


    public DrawTrainsPresenter(DrawTrainsFragment drawTrainsFragment) {
        view = drawTrainsFragment;

        gameState = gameFacade.getCurrentGame();
        faceUps = gameState.getFaceUpCards();

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

    public void drawCard(int index) {
        ArrayList<TrainCard> newList = gameState.getFaceUpCards();
        TrainCard newCard;
        switch(index) {
            case 0:
                newCard = new TrainCard(Color.WHITE);
                break;
            case 1:
                newCard = new TrainCard(Color.WILD);
                break;
            case 2:
                newCard = new TrainCard(Color.BLUE);
                break;
            case 3:
                newCard = new TrainCard(Color.WILD);
                break;
            case 4:
                newCard = new TrainCard(Color.PINK);
                break;
            default:
                newCard = new TrainCard(Color.ORANGE);
                break;

        }
        newList.set(index, newCard);
        gameState.setFaceUpCards(newList);
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
