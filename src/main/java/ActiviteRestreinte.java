package main.java;

public class ActiviteRestreinte extends Activite {

    public ActiviteRestreinte(String nom){
        super(nom, 0, 0);
    }
    public ActiviteRestreinte(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);

    }
}
