package main.java;

public class SasEntree extends Activite{

    public SasEntree(String nom){ super(nom, 0, 0); }

    public SasEntree(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);
    }
}
