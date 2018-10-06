package cs340.game.shared;

import cs340.game.shared.models.GameList;

/**
 * Created by Stephen on 10/5/2018.
 */

public class LobbyPollerResults implements Results{
    private boolean success;
    private GameList data;
    private int sequenceNumber;
    private String errorInfo;

    /**
     * The Results object to be sent from the server to client
     * @param success Whether the command executed successfully
     * @param data The result data if success is true
     * @param errorInfo Error info if success is false
     */
    public LobbyPollerResults(boolean success, GameList data, String errorInfo){
        this.success = success;
        this.data = data;
        this.errorInfo = errorInfo;
    }

    /**
     * Getter for success
     * @return Whether the command executed successfully
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Getter for data
     * @return The result data if success is true
     */
    public GameList getData() {
        return data;
    }

    /**
     * Getter for data
     * @return The result data if success is true
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Getter for errorInfo
     * @return Error info if success is false
     */
    public String getErrorInfo() {
        return errorInfo;
    }
}
