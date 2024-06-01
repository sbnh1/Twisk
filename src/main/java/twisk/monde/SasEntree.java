    package twisk.monde;

    public class SasEntree extends Activite{

        /**
         * Constructeur d'un sas d'entrée
         * Initialise un sas d'entrée avec un nom et un temps d'exécution de 0 et un écart de temps de 0
         */
        public SasEntree(){ super("SasEntree", 3, 1); }


        /**
         * Retourne une représentation en c du choix possible des lois
         * @return une représentation en c du choix des lois
         */
        private String toCChoixLoi(){
            StringBuilder s = new StringBuilder();
            s.append("if(loi == 1){\n");
            s.append("delaiUniforme(10, 4);\n");
            s.append("}\n");
            s.append("else if(loi == 2){\n");
            s.append("delaiGauss(10, 4);\n");
            s.append("}\n");
            s.append("else{\n");
            s.append("delaiExponentiel(0.1);\n");
            s.append("}\n");
            return s.toString();
        }


        /**
         * Retourne une représentation en C du sas d'entrée
         * @return Une représentation en C du sas d'entrée
         */
        public String toC(){
            StringBuilder string = new StringBuilder();
            int nbSuccesseur = this.getSuccesseur().nbEtapes();
            if(nbSuccesseur == 1){
                string.append("entrer(" + this.getNom() + ");\n");
                string.append(this.toCChoixLoi());
                string.append("transfert(SasEntree, " + this.getSuccesseur().getEtape(0).getNom() +");\n");
                string.append(this.getSuccesseur().getEtape(0).toC());
            }else if(nbSuccesseur > 1){
                string.append("{\n");
                string.append("int bifurcation_" + this.getNom() + ";\n");
                string.append("bifurcation_" + this.getNom() + " = (int)((rand() / (float) RAND_MAX ) * " + nbSuccesseur + ");\n");
                string.append("entrer(" + this.getNom() + ");\n");
                string.append("switch(bifurcation_" + this.getNom() + "){\n");
                for(int i = 0; i < nbSuccesseur; i++){
                    string.append("case " + i + ":\n");
                    string.append(this.toCChoixLoi());
                    string.append("transfert(SasEntree, " + this.getSuccesseur().getEtape(i).getNom() +");\n");
                    string.append(this.getSuccesseur().getEtape(i).toC());
                    string.append("break;\n");
                }
                string.append("}\n}\n");
            }
            return string.toString();
        }
    }
