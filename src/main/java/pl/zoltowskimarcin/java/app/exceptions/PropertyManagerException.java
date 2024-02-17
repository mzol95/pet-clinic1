package pl.zoltowskimarcin.java.app.exceptions;

public class PropertyManagerException extends Exception {

    public PropertyManagerException() {
    }

    public PropertyManagerException(String message) {
        super(message);
    }

    public PropertyManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
