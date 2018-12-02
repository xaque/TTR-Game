package cs340.game.client.Presenters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.ViewInterface.IView;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.TrainCard;

public class CardsPresenter implements Observer {

    private IView view;
    private Player currentPlayer;
    private int handCount;

    /**
     * The constructor; It initializes the numbers of cards displayed, and
     * adds the presenter to the list of observers in the gameState.
     *
     * @param cardsFragment     The view associated with this presenter
     */
    public CardsPresenter(IView cardsFragment) {
        view = cardsFragment;

        InGameFacade gameFacade = InGameFacade.getInstance();
        currentPlayer = gameFacade.getCurrentPlayer();
        handCount = 0;
        currentPlayer.addObserver(this);
    }

    /**
     *
     */
    public Map<String, String> updateHand(List<TrainCard> trainCards){
        int reds=0;
        int blues=0;
        int yellows=0;
        int blacks=0;
        int greens=0;
        int whites=0;
        int purples=0;
        int oranges=0;
        int wilds=0;

        for(int i = 0; i < trainCards.size(); i++) {
            switch(trainCards.get(i).getColor()) {
                case RED:
                    reds++;
                    break;
                case BLUE:
                    blues++;
                    break;
                case PINK:
                    purples++;
                    break;
                case WILD:
                    wilds++;
                    break;
                case BLACK:
                    blacks++;
                    break;
                case GREEN:
                    greens++;
                    break;
                case WHITE:
                    whites++;
                    break;
                case ORANGE:
                    oranges++;
                    break;
                case YELLOW:
                    yellows++;
                    break;
                default:
                    System.out.println("CARD HAS NO COLOR");
                    break;
            }
        }

        Map<String, String> hand = new HashMap<>();

        hand.put("red", Integer.toString(reds));
        hand.put("blue", Integer.toString(blues));
        hand.put("yellow", Integer.toString(yellows));
        hand.put("black", Integer.toString(blacks));
        hand.put("green", Integer.toString(greens));
        hand.put("white", Integer.toString(whites));
        hand.put("orange", Integer.toString(oranges));
        hand.put("purple", Integer.toString(purples));
        hand.put("wild", Integer.toString(wilds));

        return hand;
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        List<TrainCard> trainCards;
        if(observable instanceof Player) {
            Player player = (Player)observable;
            trainCards = player.getTrainCards();
        } else {
            trainCards = currentPlayer.getTrainCards();
        }
        if(trainCards.size() != handCount) {
            handCount = trainCards.size();
            view.update(updateHand(trainCards));
        }
    }
}
