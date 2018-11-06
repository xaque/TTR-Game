package cs340.game.shared.results;

public class ClaimRouteResults implements Results{
    boolean success;
    String errorInfo;

    public ClaimRouteResults(boolean success, String errorInfo) {
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

    public void setErrorInfo(String errorMessage) {
        this.errorInfo = errorInfo;
    }
}