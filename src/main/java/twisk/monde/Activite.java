package main.java.twisk.monde;

public class Activite extends Etape {

    private int temps;
    private int ecartTemps;

    /**
     * Constructeur d'une activité avec un nom
     * @param nom Le nom de l'activité
     */
    public Activite(String nom){
        super(nom);
        this.temps = 0;
        this.ecartTemps = 0;
    }

    /**
     * Constructeur d'une activité avec un nom, un temps d'exécution et un écart de temps
     * @param nom Le nom de l'activité
     * @param temps Le temps d'exécution de l'activité
     * @param ecartTemps L'écart de temps de l'activité
     */
    public Activite(String nom, int temps, int ecartTemps){
        super(nom);
        this.temps = temps;
        this.ecartTemps = ecartTemps;
    }

    /**
     * Vérifie si l'étape est une activité
     * @return true car l'etape est une activité
     */
    public Boolean estUneActivite(){
        return true;
    }

    /**
     * Vérifie si l'étape est un guichet
     * @return false car l'etape n'est pas un guichet
     */
    public Boolean estUnGuichet(){
        return false;
    }

    /**
     * Retourne le nombre de jetons de l'activité
     * @return Le nombre de jetons de l'activité
     */
    @Override
    public int getNbJetons() {
        return 0;
    }

    /**
     * Retourne le temps d'exécution de l'activité
     * @return Le temps d'exécution de l'activité
     */
    public int getTemps(){
        return this.temps;
    }

    /**
     * Retourne l'écart de temps de l'activité
     * @return L'écart de temps de l'activité
     */
    public int getEcartTemps(){
        return this.ecartTemps;
    }

    /**
     * Retourne une représentation en langage C de l'activité
     * @return Une représentation en langage C de l'activité
     */
    public String toC(){
        StringBuilder string = new StringBuilder();
        string.append("    delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n" +
                "    transfert(" + this.getNom() + "," + this.getSuccesseur().getEtape(0).getNom() +");\n");
        return string.toString();
    }

}
