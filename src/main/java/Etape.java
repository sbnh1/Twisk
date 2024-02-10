package main.java;

import java.util.Iterator;

public abstract class Etape implements Iterable<Etape>{

    private String nom;
    private GestionnaireEtapes successeur;

    public Etape(String nom){
        this.nom = nom;
    }

    private void ajouterSuccesseur(Etape... successeurs){
        this.successeur.ajouter(successeurs);
    }

    public Iterator<Etape> iterator(){

    }

    private abstract Boolean estUneActivite();

    private abstract Boolean estUnGuichet();


}
