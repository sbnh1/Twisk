package twisk.exceptions;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class DelaiEcartException extends Exception {
    /**
     * Constructeur d'une TwiskException
     * @param message Le message a afficher
     */
    public DelaiEcartException(String message) {
        super(message);
        afficherAlerte(message);
    }

    /**
     * Affiche une alerte à l'utilisateur
     * puis la ferme automatiquement après 3 secondes
     * @param message le message affiché
     */
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
