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
    private ArrayList<String> longestRoutesPlayerNames;

    public GameOverPresenter(GameOverActivity view){
        this.view = view;

        gameState = gameFacade.getCurrentGame();
        players = gameState.getPlayers();
        longestRoutesPlayerNames = gameState.getLongestTrackPlayerNames();
        String winner = calculateWinner();
        view.setWinner(winner);
        setPlayers();
    }

    public String calculateWinner(){
        int winningScore = 0;
        Player winningPlayer = players.get(0);

        for(Player player : players){
            int playerScore = 0;
            int dest = player.getDestinationCardRoutePointsWon();
            int lost = player.getDestinationCardRoutePointsLost();
            int points = player.getPoints();
            int lp =0;
            if(longestRoutesPlayerNames.contains(player.getName())){
                lp = 10;
            }
            playerScore = dest + points + lp - lost;

            if(playerScore > winningScore){
                winningScore = playerScore;
                winningPlayer = player;
            }
        }

        return winningPlayer.getName();
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

        //These values are not right....
        int dest = player.getDestinationCardRoutePointsWon();
        int lost = player.getDestinationCardRoutePointsLost();

        int points = player.getPoints();
        int lp;
        if(longestRoutesPlayerNames.contains(player.getName())) {
            lp = 10;
        } else {
            lp = 0;
        }

        int score = dest + points + lp;

        switch(playerNumber) {
            case 1:
                view.setPlayer1(name, Integer.toString(score), Integer.toString(dest), Integer.toString(lost), Integer.toString(lp));
                break;
            case 2:
                view.setPlayer2(name, Integer.toString(score), Integer.toString(dest), Integer.toString(lost), Integer.toString(lp));
                break;
            case 3:
                view.setPlayer3(name, Integer.toString(score), Integer.toString(dest), Integer.toString(lost), Integer.toString(lp));
                break;
            case 4:
                view.setPlayer4(name, Integer.toString(score), Integer.toString(dest), Integer.toString(lost), Integer.toString(lp));
                break;
            case 5:
                view.setPlayer5(name, Integer.toString(score), Integer.toString(dest), Integer.toString(lost), Integer.toString(lp));
                break;
        }
    }
}
