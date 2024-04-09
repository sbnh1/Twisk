package main.java.twisk.mondeIG;

public class ActiviteIG extends EtapeIG {
    /**
     * Constructeur de la classe ActiviteIG
     * @param nom nom de l'activité
     * @param larg largeur de l'activité
     * @param haut hauteur de l'activité
     */
    public ActiviteIG(String nom, int larg, int haut) {
        super(nom, larg, haut);
        this.posX = super.getPosX();
        this.posY = super.getPosY();
    }

}
