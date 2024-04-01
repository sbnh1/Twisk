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
            string.append("    delai(6,3);\n" +
                    "    transfert(SasEntree, " + this.getSuccesseur().getEtape(0).getNom() +");\n");
            return string.toString();
        }
    }
