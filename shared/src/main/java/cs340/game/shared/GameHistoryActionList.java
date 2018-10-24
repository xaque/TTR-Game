package cs340.game.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 10/23/2018.
 */

public class GameHistoryActionList {

    private List<GameHistoryAction> actions;

    public GameHistoryActionList() {
        actions = new ArrayList<>();
    }

    public List<GameHistoryAction> getActions() {
        return actions;
    }

    public int size() {
        return actions.size();
    }

    public void addAction(GameHistoryAction action) {
        actions.add(action);
    }
}
