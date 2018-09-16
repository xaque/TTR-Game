package shared;

public class Results {
    private boolean success;
    private Object data;
    private String errorInfo;

    public Results(boolean success, Object data, String errorInfo){
        this.success = success;
        this.data = data;
        this.errorInfo = errorInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getErrorInfo() {
        return errorInfo;
    }
}
