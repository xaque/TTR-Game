package cs340.game.server.DB;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.GameHistoryAction;

//TODO stub for phase 2
public class GameCommandLog {

    private List<GameHistoryAction> gameActions;
    private static GameCommandLog instance;

    public static GameCommandLog getInstance() {
        if(instance == null) {
            instance = new GameCommandLog();
        }
        return instance;
    }

    private GameCommandLog() {gameActions = new ArrayList<>();}

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
