package twisk.mondeIG;

import twisk.exceptions.DelaiEcartException;
import twisk.exceptions.TwiskException;
import twisk.outils.FabriqueIdentifiant;
import twisk.vues.EcouteurBouton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    private String nom;
    private String identifiant;
    public int posX;
    public int posY;
    private int largeur;
    private int hauteur;
    private boolean estUneEntree;
    private boolean estUneSortie;
    private int delai;
    private int ecartTemps;
    private int nbJetons;
    private boolean estUneActivite;
    private boolean estUnGuichet;

    private List<PointDeControleIG> pointDeControleIGList;

    private List<EtapeIG> successeur;
    /**
     * Constructeur de la classe abstraite EtapeIG
     * @param nom nom de l'étapeIG
     * @param larg largeur de l'étapeIG
     * @param haut hauteur de l'étapeIG
     */
    public EtapeIG(String nom, int larg, int haut, boolean estUneActivite, boolean estUnGuichet){
        this.nom = nom;
        this.largeur = larg;
        this.hauteur = haut;
        this.estUneActivite = false;
        this.estUnGuichet = false;

        this.identifiant = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        this.estUneEntree = false;
        this.estUneSortie = false;
        this.delai = 0;
        this.ecartTemps = 0;
        this.nbJetons = 0;

        Random random = new Random();
        this.posX = 100 + random.nextInt(800);
        this.posY = 100 + random.nextInt(500);

        pointDeControleIGList = new ArrayList<>();
        int milieuX = this.posX + (largeur / 2);
        int milieuY = this.posY + (hauteur / 2);

        //ajout des points de controle
        pointDeControleIGList.add(new PointDeControleIG(this.posX, milieuY, "PCGauche", this)); // Côté gauche
        pointDeControleIGList.add(new PointDeControleIG(this.posX + largeur, milieuY, "PCDroit", this)); // Côté droit
        pointDeControleIGList.add(new PointDeControleIG(milieuX, this.posY, "PCHaut", this)); // Haut
        pointDeControleIGList.add(new PointDeControleIG(milieuX, this.posY + hauteur, "PCBas", this)); // Bas

        this.successeur = new ArrayList<>();
    }

    public void ajouter(EtapeIG... etapes){
        for(EtapeIG etape : etapes){
            this.successeur.add(etape);
        }
    }

    /**
     * Récupère le nom de l'étapeIG
     * @return le nom de l'étapeIG
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Change le nom de l'étapeIG
     * @param newNom le nouveau nom de l'étapeIG
     */
    public void setNom(String nom){this.nom = nom;}

    /**
     * Récupère l'identifiant de l'étapeIG
     * @return l'identifiant de l'étapeIG
     */
    public String getIdentifiant(){
        return this.identifiant;
    }

    /**
     * Récupère la position en X de l'étapeIG
     * @return la position en X de l'étapeIG
     */
    public int getPosX(){
        return this.posX;
    }

    /**
     * Récupère la position en Y de l'étapeIG
     * @return la position Y de l'étapeIG
     */
    public int getPosY(){
        return this.posY;
    }

    /**
     * Récupère la largeur de l'étapeIG
     * @return la largeur de l'étapeIG
     */
    public int getLargeur(){
        return this.largeur;
    }

    /**
     * Récupère la hauteur de l'étapeIG
     * @return la hauteur de l'étapeIG
     */
    public int getHauteur(){
        return this.hauteur;
    }

    /**
     * Vérifie que l'étapeIG est une entrée
     * @return true si c'est une entrée, sinon renvoie false
     */
    public boolean estUneEntree(){return this.estUneEntree;}

    /**
     * Vérifie que l'étapeIG est une sortie
     * @return true si c'est une sortie, sinon renvoie false
     */
    public boolean estUneSortie(){return this.estUneSortie;}

    /**
     * Définie l'étapeIG comme une entrée
     */
    public void DefinirCommeEntree(){this.estUneEntree = true;}

    /**
     * Révoque la définition de l'étapeIG comme une entrée
     */
    public void SupprimerCommeEntree(){this.estUneEntree = false;}

    /**
     * Défini l'étapeIG comme une sortie
     */
    public void DefinirCommeSortie(){this.estUneSortie = true;}

    /**
     * Révoque la définition de l'étapeIG comme une sortie
     */
    public void SupprimerCommeSortie(){this.estUneSortie = false;}

    /**
     * Récupère le délai de l'étapeIG
     * @return le délai de l'étapeIG
     */
    public int getDelai(){return this.delai;}

    /**
     * Récupère l'écart-temps de l'étapeIG
     * @return l'écart-temps de l'étapeIG
     */
    public int getEcartTemps(){return this.ecartTemps;}

    /**
     * Défini le nouveau délai associé à l'étapeIG
     * @param newDelai le nouveau délai de l'étapeIG
     */
    public void setDelai(int newDelai){this.delai = newDelai;}

    /**
     * Défini le nouvel écart-temps associé à l'étapeIG
     * @param newEcartTemps le nouveau écart-temps de l'étapeIG qui doit être strictement inférieur au délai de l'étapeIG
     * @throws DelaiEcartException si l'écart-temps est supérieur ou égale au délai de l'étapeIG
     */
    public void setEcartTemps(int newEcartTemps) throws DelaiEcartException {
            if (newEcartTemps >= this.getDelai()) {
                throw new DelaiEcartException("Erreur : l'écart temps doit être strictement plus petit que le délai");
            } else {
                this.ecartTemps = newEcartTemps;
            }
    }

    /**
     * Défini la nouvelle position X de l'étapeIG
     * @param posX La nouvelle position X
     */
    public void setPosX(int posX){
        this.posX = posX;
    }

    /**
     * Défini la nouvelle position Y de l'étapeIG
     * @param posY La nouvelle position Y
     */
    public void setPosY(int posY){
        this.posY = posY;
    }

    /**
     * Défini la nouvelle position de tout les PointDeControleIG d'une étapeIG
     */
    public void setPosPtCtrl() {
        int milieuX = this.posX + (largeur / 2);
        int milieuY = this.posY + (hauteur / 2);
       //gauche
        pointDeControleIGList.get(0).setPositionX(this.posX);
        pointDeControleIGList.get(0).setPositionY(milieuY);
        //droite
        pointDeControleIGList.get(1).setPositionX(this.posX + getLargeur());
        pointDeControleIGList.get(1).setPositionY(milieuY);
        //haut
        pointDeControleIGList.get(2).setPositionX(milieuX);
        pointDeControleIGList.get(2).setPositionY(this.posY);
        //bas
        pointDeControleIGList.get(3).setPositionX(milieuX);
        pointDeControleIGList.get(3).setPositionY(this.posY + getHauteur());
    }

    /**
     * Retourne un itérateur sur la liste des points de controle
     * @return un itérateur sur la liste des points de controle
     */
    @Override
    public Iterator<PointDeControleIG> iterator() {
        return pointDeControleIGList.iterator();
    }

    /**
     * Retourne la liste des points de controle de l'étapeIG
     * @return la liste des points de controle
     */
    public List<PointDeControleIG> getPointsDeControle() {
        return pointDeControleIGList;
    }

    /**
     * Méthode qui récupère si une étapeIG est une activité ou non
     * @return vrai si l'étapeIG est une activité sinon non
     */
    public boolean estUneActivite(){
        return this.estUneActivite;
    }
    public boolean estUnGuichet(){
        return this.estUnGuichet;
    }

    /**
     * Méthode qui récupère le nombre de jetons d'un guichet
     * @return le nombre de jeton d'un guichet
     */
    public int getNbJetons(){
        return this.nbJetons;
    }

    /**
     * Défini le nombre de jetons d'une activité
     * @param nbJetons le nouveau nombre de jetons
     */
    public void setNbJetons(int nbJetons){
        if(!this.estUneActivite){
            this.nbJetons = nbJetons;

        }
    }
}
