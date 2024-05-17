package twisk.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

import java.awt.*;

public class VueGuichetIG extends VueEtapeIG {

    private HBox hBox;
    /**
     * Constructeur de la classe VueGuichetIG
     * @param monde le MondeIG
     * @param etape l'EtapeIG
     */
    public VueGuichetIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);
        this.hBox = new HBox();

        Region espaceGauche = new Region();
        HBox.setHgrow(espaceGauche, Priority.ALWAYS);

        // Créer un espace vide à droite
        Region espaceDroite = new Region();
        HBox.setHgrow(espaceDroite, Priority.ALWAYS);
        this.hBox.getChildren().add(espaceGauche);

        for (int i = 0; i < 7; i++) {
            Label label = new Label("  ");
            label.setPadding(new Insets(6));
            label.setStyle("-fx-border-color: #000000");
            this.hBox.getChildren().add(label);
        }
        this.hBox.getChildren().add(espaceDroite);
        this.getChildren().add(this.hBox);
    }


    @Override
    public void reagir() {}

    /**
     * Méthode qui permet de définir le nouvel emplacement de l'activité
     * @param posX la position X de l'activité
     * @param posY la position Y de l'activité
     */
    public void positionner(double posX, double posY){
        relocate(posX, posY);
    }

    /**
     * Méthode qui permet de définir la taille de l'activité
     * @param largeur la largeur de l'activité
     * @param hauteur la hauteur de l'activité
     */
    public void taille(int largeur, int hauteur) {
        this.setPrefSize(largeur, hauteur);
    }
}
