package pl.zoltowskimarcin.java.app.exceptions;


public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException() {
    }

    public AnimalNotFoundException(String message) {
        super(message);
    }

    public AnimalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
