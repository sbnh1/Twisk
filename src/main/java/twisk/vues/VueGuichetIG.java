package twisk.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;


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

        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Label label = new Label(""); // Ajouter du texte unique à chaque label
            label.setPadding(new Insets(6));
            label.setStyle("-fx-border-color: #000000");
            labels.add(label); // Ajouter le label à la liste
            this.hBox.getChildren().add(label);
        }

        this.hBox.getChildren().add(espaceDroite);
        this.getChildren().add(this.hBox);

        int test = 0;
        for (Label label : labels) {
            if (test < 4){
                Circle circle = new Circle(4); // Créer un cercle avec un rayon de 5
                circle.setFill(Color.RED); // Définir la couleur du cercle

                // Ajouter le cercle à l'intérieur du label
                HBox circleContainer = new HBox(circle);
                circleContainer.setAlignment(Pos.CENTER_RIGHT); // Aligner le cercle à droite
                label.setGraphic(circleContainer);
            } else {
                label.setText(" ");
            }
            test++;
        }
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
