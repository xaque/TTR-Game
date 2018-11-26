package cs340.game.client.Presenters;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.CardsFragment;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.TrainCard;

public class CardsPresenter implements Observer {

    private CardsFragment view;

    private Player currentPlayer;

    /**
     * The constructor; It initializes the numbers of cards displayed, and
     * adds the presenter to the list of observers in the gameState.
     *
     * @param cardsFragment     The view associated with this presenter
     */
    public CardsPresenter(CardsFragment cardsFragment) {
        view = cardsFragment;

        InGameFacade gameFacade = InGameFacade.getInstance();
        currentPlayer = gameFacade.getCurrentPlayer();
        GameState gameState = gameFacade.getCurrentGame();
        updateHand();
        gameFacade.addObserver(this);
    }

    /**
     *
     */
    public void updateHand(){
        int reds=0;
        int blues=0;
        int yellows=0;
        int blacks=0;
        int greens=0;
        int whites=0;
        int purples=0;
        int oranges=0;
        int wilds=0;

        for(TrainCard card : currentPlayer.getTrainCards()) {
            switch(card.getColor()) {
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

            }
        }

        view.setRed_count(Integer.toString(reds));
        view.setBlue_count(Integer.toString(blues));
        view.setYellow_count(Integer.toString(yellows));
        view.setBlack_count(Integer.toString(blacks));
        view.setGreen_count(Integer.toString(greens));
        view.setWhite_count(Integer.toString(whites));
        view.setOrange_count(Integer.toString(oranges));
        view.setPurple_count(Integer.toString(purples));
        view.setWild_count(Integer.toString(wilds));
    }

    /**
     *
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        view.update();
    }
}
