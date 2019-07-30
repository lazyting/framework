package exception;

public class ToolException extends RuntimeException {

    private Integer status;
    private String message;

    public ToolException() {
        super();
        this.status = -1;
    }

    public ToolException(Integer status) {
        this.status = status;
    }

    public ToolException(String message) {
        this.message = message;
        this.status = -1;
    }

    public ToolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToolException(Throwable cause) {
        super(cause);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
