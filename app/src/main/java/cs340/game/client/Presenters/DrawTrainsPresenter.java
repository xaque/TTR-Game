package cs340.game.client.Presenters;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.DrawTrainsFragment;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.TrainCard;

public class DrawTrainsPresenter implements Observer {

    private DrawTrainsFragment view;
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private List<TrainCard> faceUps;


    public DrawTrainsPresenter(DrawTrainsFragment drawTrainsFragment) {
        view = drawTrainsFragment;

        gameState = gameFacade.getCurrentGame();
        faceUps = gameState.getFaceUpCards();

        //Get cards left in deck

        setFaceUps(faceUps);

    }

    public void setFaceUps(List<TrainCard> cards) {
        for(int i=1; i<=cards.size(); i++) {
            view.setCard(i, cards.get(i-1).getColor());
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}