package main.java.twisk.monde;

public class SasSortie extends Activite{
    public SasSortie(String nom){
        super(nom,0,0);
    }

    public SasSortie(String nom, int temps, int ecartTemps){
        super(nom, temps, ecartTemps);
    }

    public StringBuilder toC(){
        StringBuilder string = new StringBuilder();
        string.append("");
        return string;
    }

}

