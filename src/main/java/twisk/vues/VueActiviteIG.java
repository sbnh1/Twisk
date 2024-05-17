package twisk.vues;

import javafx.geometry.Pos;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
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
        hbox.setPrefSize(etape.getLargeur(), etape.getHauteur());
        hbox.setStyle("-fx-background-color: grey;-fx-background-radius: 15.0;-fx-border-color: black;");
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