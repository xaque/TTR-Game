package cs340.game.shared.results;

import cs340.game.shared.GameHistoryActionList;

/**
 * Created by Stephen on 10/23/2018.
 */

public class GamePollerResults implements Results{
    private boolean success;
    private GameHistoryActionList data;
    private int sequenceNumber;
    private String errorInfo;

    /**
     * The Results object to be sent from the server to client
     * @param success Whether the command executed successfully
     * @param data The result data if success is true
     * @param errorInfo Error info if success is false
     */
    public GamePollerResults(boolean success, GameHistoryActionList data, int sequenceNumber, String errorInfo){
        this.success = success;
        this.data = data;
        this.sequenceNumber = sequenceNumber;
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
    public GameHistoryActionList getData() {
        return data;
    }

    /**
     * Getter for sequenceNumber
     * @return The result data if success is true
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Setter for sequenceNumber
     * @param sequenceNumber the number sequence number
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Getter for errorInfo
     * @return Error info if success is false
     */
    public String getErrorInfo() {
        return errorInfo;
    }
}
