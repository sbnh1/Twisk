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
        if(this.nbSuccesseurs() == 0){
            string.append("");
            return string.toString();
        } else {
            string.append("    delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n");
            string.append("    transfert(" + this.getNom() + ", " + this.getSuccesseur().getEtape(0).getNom() +");\n");
            string.append(this.getSuccesseur().getEtape(0).toC());
            return string.toString();
        }
    }

}