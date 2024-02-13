package pl.zoltowskimarcin.java.app.exceptions;


public class AnimalCreateFaultException extends Exception {

    public AnimalCreateFaultException() {
    }

    public AnimalCreateFaultException(String message) {
        super(message);
    }

    public AnimalCreateFaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
