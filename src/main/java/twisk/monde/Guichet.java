package twisk.monde;

import twisk.outils.FabriqueNumero;
public class Guichet extends Etape {

    private int nbJetons;
    private int semaphores;


    /**
     * Constructeur d'un guichet avec un nom
     * @param nom Le nom du guichet
     */
    public Guichet(String nom){
        super(nom);
        this.nbJetons = 0;
        this.semaphores = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Constructeur d'un guichet avec un nom et un nombre de jetons initial
     * @param nom Le nom du guichet
     * @param nbJetons Le nombre de jetons initial du guichet
     */
    public Guichet(String nom, int nbJetons){
        super(nom);
        this.nbJetons = nbJetons;
        this.semaphores = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Vérifie si l'étape est un guichet
     * @return true car l'etape est un guichet
     */
    public Boolean estUnGuichet(){
        return true;
    }

    /**
     * Vérifie si l'étape est une activité
     * @return false car l'etape n'est pas une activité
     */
    public Boolean estUneActivite(){
        return false;
    }

    /**
     * Retourne le nombre de jetons du guichet
     * @return Le nombre de jetons du guichet
     */
    public int getNbJetons(){
        return this.nbJetons;
    }

    /**
     * Retourne le numéro de sémaphore du guichet
     * @return Le numéro de sémaphore du guichet
     */
    public int getNumeroSemaphore(){
        return this.semaphores;
    }

    /**
     * Retourne une représentation en C du guichet
     * @return Une représentation en C du guichet
     */
    public String toC(){
        StringBuilder string = new StringBuilder();
        string.append("P(ids, " + this.getNom() + "_semaphore);\n");
        string.append("transfert(" + this.getNom() + ", " + this.getSuccesseur().getEtape(0).getNom() + ");\n");
        string.append("delai(" + this.getSuccesseur().getEtape(0).getTemps() + "," + this.getSuccesseur().getEtape(0).getEcartTemps() + ");\n");
        string.append("V(ids, " + this.getNom() + "_semaphore);\n");
        string.append(this.getSuccesseur().getEtape(0).toC());

        return string.toString();
    }
}
