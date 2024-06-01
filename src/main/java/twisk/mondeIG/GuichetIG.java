package twisk.mondeIG;

public class GuichetIG extends EtapeIG{
    /**
     * Constructeur de la classe GuichetIG
     * @param nom nom de l'activité
     * @param larg largeur de l'activité
     * @param haut hauteur de l'activité
     */
    public GuichetIG(String nom, int larg, int haut){
        super(nom, larg, haut, false, true);
        this.posX = super.getPosX();
        this.posY = super.getPosY();
    }

    public GuichetIG(String nom, int larg, int haut, String identifiant){
        super(nom, larg, haut, false, true, identifiant);
        this.posX = super.getPosX();
        this.posY = super.getPosY();
    }
}
