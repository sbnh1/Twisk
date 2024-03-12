package main.java.twisk.monde;

public class ActiviteRestreinte extends Activite {

    public ActiviteRestreinte(String nom){
        super(nom, 0, 0);
    }
    public ActiviteRestreinte(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);
    }

    public String toC(){
        return "transfert(" + this.getId() + "," + this.getSuccesseur().getEtape(0) +");\n";
    }
}