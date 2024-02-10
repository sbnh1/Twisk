package main.java;

import java.util.Iterator;

public abstract class Etape implements Iterable<Etape>{

    private String nom;
    private GestionnaireEtapes successeur;

    public Etape(String nom){
        this.nom = nom;
        this.successeur = new GestionnaireEtapes();
    }

    private void ajouterSuccesseur(Etape... successeurs){
        this.successeur.ajouter(successeurs);
    }

    public Iterator<Etape> iterator(){
        return successeur.iterator();
    }

    protected abstract Boolean estUneActivite();

    protected abstract Boolean estUnGuichet();


}
