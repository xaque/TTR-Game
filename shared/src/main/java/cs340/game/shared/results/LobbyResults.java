package cs340.game.shared.results;

/**
 * Created by Stephen on 10/5/2018.
 */

public class LobbyResults implements Results {
    private boolean success;
    private String errorInfo;

    /**
     * The Results object to be sent from the server to client
     * @param success Whether the command executed successfully
     * @param errorInfo Error info if success is false
     */
    public LobbyResults(boolean success, String errorInfo){
        this.success = success;
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
     * Getter for errorInfo
     * @return Error info if success is false
     */
    public String getErrorInfo() {
        return errorInfo;
    }
}
