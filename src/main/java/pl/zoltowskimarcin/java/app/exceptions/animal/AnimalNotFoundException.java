package pl.zoltowskimarcin.java.app.exceptions.animal;


public class AnimalNotFoundException extends Exception {

    public AnimalNotFoundException() {
    }

    public AnimalNotFoundException(String message) {
        super(message);
    }

    public AnimalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
