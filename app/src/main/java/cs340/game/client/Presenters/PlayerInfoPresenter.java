package cs340.game.client.Presenters;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.PlayerInfoFragment;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;

public class PlayerInfoPresenter implements Observer {
    private PlayerInfoFragment view;
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private List<Player> players;

    public PlayerInfoPresenter(PlayerInfoFragment playerInfoFragment){
        view = playerInfoFragment;
        gameState = gameFacade.getCurrentGame();
        players = gameState.getPlayers();

        setPlayers();
    }

    public void setPlayers() {
        for(int i=1; i<6; i++) {
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
        String score = Integer.toString(player.getPoints());
        String trains = Integer.toString(player.getTrainTokens());
        String cards = Integer.toString(player.getTrainCards().size());
        String dest = Integer.toString(player.getDestinationCards().size());

        switch(playerNumber) {
            case 1:
                view.setPlayer1(name, score, trains, cards, dest);
                break;
            case 2:
                view.setPlayer2(name, score, trains, cards, dest);
                break;
            case 3:
                view.setPlayer3(name, score, trains, cards, dest);
                break;
            case 4:
                view.setPlayer4(name, score, trains, cards, dest);
                break;
            case 5:
                view.setPlayer5(name, score, trains, cards, dest);
                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
