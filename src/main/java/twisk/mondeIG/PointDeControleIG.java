package twisk.mondeIG;

import com.google.gson.annotations.Expose;
import twisk.outils.FabriqueIdPc;

public class PointDeControleIG {
    private double positionX;
    private double positionY;
    private String identifiant;

    @Expose(serialize = false, deserialize = false)
    private transient EtapeIG etapeIG;

    @Expose(serialize = false, deserialize = false)
    private transient boolean estSelectionnee;
    private boolean sensCirculation;

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
        this.identifiant = identifiant + etapeIG.getIdentifiant();
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
     * Définit l'état de sélection.
     * @param estSelectionnee le nouvel état de sélection
     */
    public void setEstSelectionnee(boolean estSelectionnee) {
        this.estSelectionnee = estSelectionnee;
    }

    /**
     * Obtient le sens de circulation.
     * @return le sens de circulation
     */
    public boolean getSensCirculation() {
        return sensCirculation;
    }

    /**
     * Définit le sens de circulation.
     * @param sensCirculation le nouveau sens de circulation
     */
    public void setSensCirculation(boolean sensCirculation) {
        this.sensCirculation = sensCirculation;
    }

    /**
     * Définit l'identifiant.
     * @param identifiant le nouvel identifiant
     */
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

}
