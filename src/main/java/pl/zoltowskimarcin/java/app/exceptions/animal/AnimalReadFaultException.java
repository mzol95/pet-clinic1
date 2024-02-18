package pl.zoltowskimarcin.java.app.exceptions.animal;

public class AnimalReadFaultException extends Exception {
    public AnimalReadFaultException() {
    }

    public AnimalReadFaultException(String message) {
        super(message);
    }

    public AnimalReadFaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
