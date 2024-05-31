package twisk.exceptions;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class PointDeControleException extends TwiskException{

    /**
     * Constructeur d'un PointDeControleException
     * @param message Le message a afficher
     */
    public PointDeControleException(String message) {
        super();
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
