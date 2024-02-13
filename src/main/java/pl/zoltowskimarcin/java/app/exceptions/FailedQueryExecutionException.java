package pl.zoltowskimarcin.java.app.exceptions;


public class FailedQueryExecutionException extends Exception {

    public FailedQueryExecutionException() {
    }

    public FailedQueryExecutionException(String message) {
        super(message);
    }

    public FailedQueryExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
