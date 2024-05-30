package twisk.exceptions;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class MondeInvalideException extends TwiskException{
    /**
     * Constructeur MondeInvalideException
     * @param message Le message a afficher
     */
    public MondeInvalideException(String message){
        super(message);
        afficherAlerte(message);
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        PauseTransition pause = new PauseTransition(Duration.millis(3000));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }
}
