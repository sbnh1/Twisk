package main.java;

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
}
