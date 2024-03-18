package main.java.twisk.monde;

public class ActiviteRestreinte extends Activite {

    public ActiviteRestreinte(String nom){
        super(nom, 0, 0);
    }
    public ActiviteRestreinte(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);
    }

    public StringBuilder toC(){
        StringBuilder string = new StringBuilder();
        string.append("    delai(6,2);\n");
        string.append("    transfert(" + this.getNom() + "," + this.getSuccesseur().getEtape(0) +");\n");
        return string;
    }

}