package main.java.twisk.vues;

import main.java.twisk.mondeIG.EtapeIG;
import main.java.twisk.mondeIG.MondeIG;

public class VueGuichetIG extends VueEtapeIG {

    /**
     * Constructeur de la classe VueGuichetIG
     * @param monde le MondeIG
     * @param etape l'EtapeIG
     */
    public VueGuichetIG(MondeIG monde, EtapeIG etape){
        super(monde,etape);
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