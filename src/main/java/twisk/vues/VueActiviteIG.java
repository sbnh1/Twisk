package main.java.twisk.vues;

import javafx.geometry.Pos;
import main.java.twisk.mondeIG.EtapeIG;
import main.java.twisk.mondeIG.MondeIG;
import javafx.scene.layout.HBox;
import twisk.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG {

    /**
     * Constructeur de la classe VueActivitéIG
     * @param monde le mondeIG
     * @param etape L'étapeIG
     */
    public VueActiviteIG(MondeIG monde, EtapeIG etape){
        super(monde, etape);
        HBox hbox = new HBox();
        hbox.setPrefSize(40, 35);
        hbox.setStyle("-fx-background-color: grey;-fx-background-radius: 15.0;");
        getChildren().add(hbox);
    }

    @Override
    public void reagir() {
    }

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