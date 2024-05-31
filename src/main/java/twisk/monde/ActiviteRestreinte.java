package twisk.monde;

public class ActiviteRestreinte extends Activite {

    /**
     * Constructeur d'une activité restreinte avec un nom
     * @param nom Le nom de l'activité restreinte
     */
    public ActiviteRestreinte(String nom){
        super(nom, 4, 1);
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
        int nbSuccesseur = this.nbSuccesseurs();
        if(nbSuccesseur == 1){
            string.append("    delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n");
            //faire ici le switch
            string.append("    transfert(" + this.getNom() + ", " + this.getSuccesseur().getEtape(0).getNom() +");\n");
            string.append(this.getSuccesseur().getEtape(0).toC());
        } else if (nbSuccesseur > 1){
            string.append("    int bifurcation_" + this.getNom() + " = (int)((rand() / (float) RAND_MAX ) * " + nbSuccesseur + ");\n");
            string.append("    switch(bifurcation_" + this.getNom() + "){\n");
            for(int i = 0; i < nbSuccesseur; i++) {
                string.append("        case " + i + ":\n");
                string.append("    delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n");
                string.append("    transfert(" + this.getNom() + ", " + this.getSuccesseur().getEtape(i).getNom() +");\n");
                string.append(this.getSuccesseur().getEtape(i).toC());
                string.append("        break;\n");
            }
            string.append("}\n");
        }
        return string.toString();
    }

}