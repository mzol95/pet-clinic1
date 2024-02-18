package pl.zoltowskimarcin.java.app.exceptions.animal;

public class AnimalUpdateFaultException extends Exception {
    public AnimalUpdateFaultException() {
    }

    public AnimalUpdateFaultException(String message) {
        super(message);
    }

    public AnimalUpdateFaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
