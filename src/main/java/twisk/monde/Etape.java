package main.java.twisk.monde;

import java.util.Iterator;
import main.java.twisk.outils.FabriqueNumero;

public abstract class Etape implements Iterable<Etape>{

    private String nom;
    private GestionnaireEtapes successeur;
    private int id;

    public Etape(String nom){
        this.nom = nom;
        this.successeur = new GestionnaireEtapes();
        this.id = FabriqueNumero.getInstance().getNumeroEtape();
    }

    public int getId(){
        return this.id;
    }
    public String getNom(){return this.nom;}
    public void ajouterSuccesseur(Etape... successeurs){
        this.successeur.ajouter(successeurs);
    }

    public GestionnaireEtapes getSuccesseur() {
        return this.successeur;
    }

    public Iterator<Etape> iterator(){
        return successeur.iterator();
    }

    public abstract Boolean estUneActivite();

    public abstract Boolean estUnGuichet();

    public int nbSuccesseurs() {
        return this.successeur.nbEtapes();
    }

    public String toString(){
        String res;
        res = this.nom + ": " + this.nbSuccesseurs() + ": ";

        for(Etape etape : this.successeur){
            res += etape.nom + " ";
        }

        return res;
    }

    public abstract String toC();
}
