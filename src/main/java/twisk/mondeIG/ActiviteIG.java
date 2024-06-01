package twisk.mondeIG;

public class ActiviteIG extends EtapeIG {
    private boolean estRestreinte;
    /**
     * Constructeur de la classe ActiviteIG
     * @param nom nom de l'activité
     * @param larg largeur de l'activité
     * @param haut hauteur de l'activité
     */
    public ActiviteIG(String nom, int larg, int haut) {
        super(nom, larg, haut, true, false);
        this.posX = super.getPosX();
        this.posY = super.getPosY();
    }

    public ActiviteIG(String nom, int larg, int haut, String identifiant) {
        super(nom, larg, haut, true, false, identifiant);
        this.posX = super.getPosX();
        this.posY = super.getPosY();
    }

    /**
     *  renvoie vrai si c'est une activite restreinte
     */
    public boolean estRestreinte(){
        return this.estRestreinte;
    }

    /**
     * Constructeur de la classe ActiviteIG
     * @param val valeur a definir
     */
    public void setEstRestreinte(boolean val){
        this.estRestreinte = val;
    }
}
