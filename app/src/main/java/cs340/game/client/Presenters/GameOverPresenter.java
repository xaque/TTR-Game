package cs340.game.client.Presenters;

import java.util.ArrayList;

import cs340.game.client.InGameFacade;
import cs340.game.client.Views.GameOverActivity;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.Player;

public class GameOverPresenter {

    private GameOverActivity view;
    private InGameFacade gameFacade = InGameFacade.getInstance();
    private GameState gameState;
    private ArrayList<Player> players;

    public GameOverPresenter(GameOverActivity view){
        this.view = view;

        gameState = gameFacade.getCurrentGame();
        players = gameState.getPlayers();
        gameFacade.stopPoller();

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

        //These values are not right....
        String dest = Integer.toString(player.getTrainTokens());
        String points = Integer.toString(player.getTrainCards().size());
        String lp = Integer.toString(player.getDestinationCards().size());

        switch(playerNumber) {
            case 1:
                view.setPlayer1(name, score, dest, points, lp);
                break;
            case 2:
                view.setPlayer2(name, score, dest, points, lp);
                break;
            case 3:
                view.setPlayer3(name, score, dest, points, lp);
                break;
            case 4:
                view.setPlayer4(name, score, dest, points, lp);
                break;
            case 5:
                view.setPlayer5(name, score, dest, points, lp);
                break;
        }
    }
}
