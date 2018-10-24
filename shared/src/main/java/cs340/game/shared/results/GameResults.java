package cs340.game.shared.results;

/**
 * Created by Stephen on 10/23/2018.
 */

public class GameResults implements Results{
    private boolean success;
    private String errorInfo;

    public GameResults(boolean success, String errorInfo) {
        this.success = success;
        this.errorInfo = errorInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
