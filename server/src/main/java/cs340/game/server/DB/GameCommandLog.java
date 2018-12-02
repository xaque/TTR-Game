package cs340.game.server.DB;

import java.util.ArrayList;

import cs340.game.shared.GameHistoryAction;

public class GameCommandLog {

    private ArrayList<GameHistoryAction> gameActions;

    public GameCommandLog() {gameActions = new ArrayList<>();}

    public void addGameCommand(GameHistoryAction action) {
        gameActions.add(action);
    }

    public int getLogLength() {
        return gameActions.size();
    }

    public GameHistoryAction getGameCommandAtIndex(int index) {
        return gameActions.get(index);
    }
}
