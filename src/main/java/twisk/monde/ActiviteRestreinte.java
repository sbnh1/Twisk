package main.java.twisk.monde;

public class ActiviteRestreinte extends Activite {

    /**
     * Constructeur d'une activité restreinte avec un nom
     * @param nom Le nom de l'activité restreinte
     */
    public ActiviteRestreinte(String nom){
        super(nom, 0, 0);
    }

    /**
     * Constructeur d'une activité restreinte avec un nom, un temps d'exécution et un écart de temps
     * @param nom Le nom de l'activité restreinte
     * @param temps Le temps d'exécution de l'activité restreinte
     * @param ecartTemps L'écart de temps de l'activité restreinte
     */
    public ActiviteRestreinte(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);
    }

    /**
     * Retourne une représentation en langage C de l'activité restreinte
     * @return Une représentation en langage C de l'activité restreinte
     */
    public String toC(){
        StringBuilder string = new StringBuilder();
        string.append("    delai(6,2);\n");
        string.append("    transfert(" + this.getNom() + ", " + this.getSuccesseur().getEtape(0).getNom() +");\n");
        return string.toString();
    }

}