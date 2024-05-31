package twisk.mondeIG;

public class PointDeControleIG {
    private double positionX;
    private double positionY;
    private String identifiant;
    private EtapeIG etapeIG;
    private boolean estSelectionnee;

    /**
     * Constructeur de la classe PointDeControleIG
     * @param positionX la position X du PointDeControleIG
     * @param positionY la position Y du PointDeControleIG
     * @param identifiant l'identifiant du PointDeControleIG
     * @param etapeIG l'étape associé au PointDeControleIG
     */
    public PointDeControleIG(double positionX, double positionY, String identifiant, EtapeIG etapeIG){
        this.positionX = positionX;
        this.positionY = positionY;
        this.identifiant = identifiant;
        this.etapeIG = etapeIG;
        this.estSelectionnee = false;
    }

    /**
     * Retourne la position X du PointDeControleIG
     * @return la position X du PointDeControleIG
     */
    public double getPositionX(){
        return this.positionX;
    }

    /**
     * Retourne la position Y du PointDeControleIG
     * @return la position Y du PointDeControleIG
     */
    public double getPositionY(){
        return this.positionY;
    }

    /**
     * Retourne l'identifiant associé au PointDeControleIG
     * @return l'identifiant associé au PointDeControleIG
     */
    public String getIdentifiant(){
        return this.identifiant;
    }

    /**
     * Retourne l'étapeIG associé au PointDeControleIG
     * @return l'étapeIG associé au PointDeControleIG
     */
    public EtapeIG getEtapeIG(){
        return this.etapeIG;
    }

    /**
     * Modifie la position X du PointDeControleIG avec la position donné en paramètre
     * @param positionX la nouvelle position X du PointDeControleIG
     */
    public void setPositionX(double positionX){
        this.positionX = positionX;
    }

    /** Modifie la position Y du PointDeControleIG avec la position donné en paramètre
     * @param positionY la nouvelle position Y du PointDeControleIG
     */
    public void setPositionY(double positionY){
        this.positionY = positionY;
    }

    /**
     * @return un booléen true si sélectionné, sinon false
     */
    public boolean estSelectionee(){
        return this.estSelectionnee;
    }

    /**
     * Modifie le booléen estSelectionnee avec celui en paramètre
     * @param estSelectionnee le nouvel état du point de contrôle
     */
    public void setEstSelectionnee(boolean estSelectionnee) {
        this.estSelectionnee = estSelectionnee;
    }
}
