package pl.zoltowskimarcin.java.app.exceptions.animal;

public class AnimalDeleteFaultException extends Exception {
    public AnimalDeleteFaultException() {
    }

    public AnimalDeleteFaultException(String message) {
        super(message);
    }

    public AnimalDeleteFaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
