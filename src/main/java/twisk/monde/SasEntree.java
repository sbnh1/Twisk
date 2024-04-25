    package main.java.twisk.monde;

    public class SasEntree extends Activite{

        /**
         * Constructeur d'un sas d'entrée
         * Initialise un sas d'entrée avec un nom et un temps d'exécution de 0 et un écart de temps de 0
         */
        public SasEntree(){ super("SasEntree", 0, 0); }

        /**
         * Retourne une représentation en langage C du sas d'entrée
         * @return Une représentation en langage C du sas d'entrée
         */
        public String toC(){
            StringBuilder string = new StringBuilder();
            int nbSuccesseur = this.getSuccesseur().nbEtapes();
            if(nbSuccesseur == 1){
                string.append("    entrer(" + this.getNom() + ");\n");
                string.append("    transfert(SasEntree, " + this.getSuccesseur().getEtape(0).getNom() +");\n");
                string.append(this.getSuccesseur().getEtape(0).toC());
            }else if(nbSuccesseur > 1){
                string.append("    int bifurcation_" + this.getNom() + " = (int)((rand() / (float) RAND_MAX ) * " + nbSuccesseur + ");\n");
                string.append("    entrer(" + this.getNom() + ");\n");
                string.append("    switch(bifurcation_" + this.getNom() + "){\n");
                for(int i = 0; i < nbSuccesseur; i++){
                    string.append("        case " + i + ":\n");
                    string.append("            transfert(SasEntree, " + this.getSuccesseur().getEtape(i).getNom() +");\n");
                    string.append(this.getSuccesseur().getEtape(i).toC());
                    string.append("            break;\n");
                }
                string.append("    }\n");
            }
            return string.toString();
        }
    }
