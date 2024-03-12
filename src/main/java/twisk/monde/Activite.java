package main.java.twisk.monde;

public class Activite extends Etape {

    private int temps;
    private int ecartTemps;

    public Activite(String nom){
        super(nom);
        this.temps = 0;
        this.ecartTemps = 0;
    }

    public Activite(String nom, int temps, int ecartTemps){
        super(nom);
        this.temps = temps;
        this.ecartTemps = ecartTemps;
    }

    public Boolean estUneActivite(){
        return true;
    }

    public Boolean estUnGuichet(){
        return false;
    }

    public int getTemps(){
        return this.temps;
    }

    public int getEcartTemps(){
        return this.ecartTemps;
    }

    public String toC(){
        return "delai(" + this.getTemps() + "," + this.getEcartTemps() + ");\n" +
                "transfert(" + this.getId() + "," + this.getSuccesseur().getEtape(0) +");\n";
    }

}
