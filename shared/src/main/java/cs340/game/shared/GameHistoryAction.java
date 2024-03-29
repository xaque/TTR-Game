package cs340.game.shared;

import java.io.Serializable;

/**
 * Created by Stephen on 10/23/2018.
 */

public class GameHistoryAction implements Serializable {
    //TODO add a CommandType or similar identifier?
    //TODO change mapChange to GameStateDiff, implement all necessary changes in Commands
    private String actionMessage;
    private Object mapChange; // this will likely be a claimed route that can be sent to update the Client but could be any object to return

    public GameHistoryAction(String actionMessage, Object mapChange) {
        this.actionMessage = actionMessage;
        this.mapChange = mapChange;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public Object getMapChange() {
        return mapChange;
    }

    public void setMapChange(Object mapChange) {
        this.mapChange = mapChange;
    }
}
