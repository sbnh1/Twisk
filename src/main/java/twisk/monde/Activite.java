package twisk.monde;

public class Activite extends Etape {

    private int temps;
    private int ecartTemps;

    /**
     * Constructeur d'une activité avec un nom
     * @param nom Le nom de l'activité
     */
    public Activite(String nom){
        super(nom);
        this.temps = 4;
        this.ecartTemps = 1;
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
    @Override
    public int getTemps(){
        return this.temps;
    }
    /**
     * Retourne l'écart de temps de l'activité
     * @return L'écart de temps de l'activité
     */
    @Override
    public int getEcartTemps(){
        return this.ecartTemps;
    }

    /**
     * Retourne une représentation en C de l'activité
     * @return Une représentation en C de l'activité
     */
    public String toC(){
        StringBuilder string = new StringBuilder();
        int nbSuccesseur = this.nbSuccesseurs();
        if(nbSuccesseur == 1){
            string.append("delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n");
            string.append("transfert(" + this.getNom() + "," + this.getSuccesseur().getEtape(0).getNom() +");\n");
            string.append(this.getSuccesseur().getEtape(0).toC());

        } else if (nbSuccesseur > 1){
            string.append("{\n");
            string.append("int bifurcation_" + this.getNom() + ";\n");
            string.append("bifurcation_" + this.getNom() + " = (int)((rand() / (float) RAND_MAX ) * " + nbSuccesseur + ");\n");
            string.append("switch(bifurcation_" + this.getNom() + "){\n");

            for(int i = 0; i < nbSuccesseur; i++) {
                string.append("case " + i + ":\n");
                string.append("delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n");
                string.append("transfert(" + this.getNom() + "," + this.getSuccesseur().getEtape(i).getNom() + ");\n");
                string.append(this.getSuccesseur().getEtape(i).toC());
                string.append("break;\n");
            }
            string.append("}\n}\n");
        }
        return string.toString();
    }
}
