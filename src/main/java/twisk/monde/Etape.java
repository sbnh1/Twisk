package twisk.monde;

import java.util.Iterator;
import twisk.outils.FabriqueNumero;

public abstract class Etape implements Iterable<Etape>{

    private String nom;
    private GestionnaireEtapes successeur;
    private int id;

    /**
     * Constructeur de la classe Etape
     * @param nom Le nom de l'étape
     */
    public Etape(String nom){
        this.nom = nom;
        this.successeur = new GestionnaireEtapes();
        this.id = FabriqueNumero.getInstance().getNumeroEtape();
    }

    /**
     * Retourne l'identifiant de l'étape
     * @return L'identifiant de l'étape
     */
    public int getId(){
        return this.id;
    }

    /**
     * Retourne le nom de l'étape
     * @return Le nom de l'étape
     */
    public String getNom(){return this.nom;}

    /**
     * Ajoute un ou plusieurs successeurs à l'étape
     * @param successeurs les étapes successrices de l'etappe
     */
    public void ajouterSuccesseur(Etape... successeurs){
        this.successeur.ajouter(successeurs);
    }

    /**
     * Retourne le gestionnaire des étapes successrices de l'étape.
     * @return Le gestionnaire des étapes successeurs.
     */
    public GestionnaireEtapes getSuccesseur() {
        return this.successeur;
    }

    /**
     * Retourne un itérateur sur les étapes successrices de l'étape
     * @return Un itérateur sur les étapes successrices
     */
    public Iterator<Etape> iterator(){
        return successeur.iterator();
    }

    /**
     * Vérifie si l'étape est une activité
     * @return true si l'étape est une activité, false sinon
     */
    public abstract Boolean estUneActivite();

    /**
     * Vérifie si l'étape est un guichet
     * @return true si l'étape est un guichet, false sinon
     */
    public abstract Boolean estUnGuichet();

    /**
     * Retourne le nombre de jetons de l'étape
     * @return Le nombre de jetons de l'étape
     */
    public abstract int getNbJetons();

    /**
     * Retourne le nombre de successeurs de l'étape
     * @return Le nombre de successeurs de l'étape
     */
    public int nbSuccesseurs() {
        return this.successeur.nbEtapes();
    }

    /**
     * Retourne l'étape sous forme écrite
     * @return l'étape sous forme écrite
     */
    public String toString(){
        String res;
        res = this.nom + ": " + this.nbSuccesseurs() + ": ";

        for(Etape etape : this.successeur){
            res += etape.nom + " ";
        }

        return res;
    }

    /**
     * Retourne une représentation en langage C de l'étape
     * @return Une représentation en langage C de l'étape
     */
    public abstract String toC();
}
