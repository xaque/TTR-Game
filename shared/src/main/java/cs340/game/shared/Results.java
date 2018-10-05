package cs340.game.shared;

import java.io.Serializable;

public class Results implements Serializable {
    private boolean success;
    private Object data;
    private String errorInfo;

    /**
     * The Results object to be sent from the server to client
     * @param success Whether the command executed successfully
     * @param data The result data if success is true
     * @param errorInfo Error info if success is false
     */
    public Results(boolean success, Object data, String errorInfo){
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
    public Object getData() {
        return data;
    }

    /**
     * Getter for errorInfo
     * @return Error info if success is false
     */
    public String getErrorInfo() {
        return errorInfo;
    }
}
