package cs340.game.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen on 10/23/2018.
 */

public class GameHistoryActionList implements Serializable {

    private ArrayList<GameHistoryAction> actions;

    public GameHistoryActionList() {
        actions = new ArrayList<>();
    }

    public ArrayList<GameHistoryAction> getActions() {
        return actions;
    }

    public int getSize() {
        return actions.size();
    }

    public void addAction(GameHistoryAction action) {
        actions.add(0, action);
    }
}
