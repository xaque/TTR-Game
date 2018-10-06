package cs340.game.shared;

/**
 * Created by Stephen on 10/5/2018.
 */

public class LoginResults implements Results {
    private boolean success;
    private String authToken;
    private String errorInfo;

    /**
     * The Results object to be sent from the server to client
     * @param success Whether the command executed successfully
     * @param authToken The result authToken if success is true
     * @param errorInfo Error info if success is false
     */
    public LoginResults(boolean success, String authToken, String errorInfo){
        this.success = success;
        this.authToken = authToken;
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
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Getter for errorInfo
     * @return Error info if success is false
     */
    public String getErrorInfo() {
        return errorInfo;
    }
}
