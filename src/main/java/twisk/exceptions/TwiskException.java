package twisk.exceptions;

public class TwiskException extends Exception {

    /**
     * Constructeur d'une TwiskException
     * @param message Le message a afficher
     */
    public TwiskException(String message) {
        super(message);
    }
}