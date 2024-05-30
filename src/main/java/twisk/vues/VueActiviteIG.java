package twisk.vues;

import javafx.geometry.Pos;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import javafx.scene.layout.HBox;
import twisk.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG {
    private HBox hbox;

    /**
     * Constructeur de la classe VueActivitéIG
     * @param monde le mondeIG
     * @param etape L'étapeIG
     */
    public VueActiviteIG(MondeIG monde, EtapeIG etape){
        super(monde, etape);
        hbox = new HBox();
        hbox.setPrefSize(etape.getLargeur(), etape.getHauteur());
        hbox.setStyle("-fx-border-color: #0000FF; -fx-background-color: #FFFFFF; -fx-border-width: 3px 0 0 0;-fx-border-radius: 2.0;");
        getChildren().add(hbox);
    }

    @Override
    public void reagir() {
    }

    public HBox getHbox(){
        return this.hbox;
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