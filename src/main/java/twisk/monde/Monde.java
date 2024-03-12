package main.java.twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape>{

    private GestionnaireEtapes etapes;
    private Etape entree;
    private Etape sortie;

    public Monde(){
        this.etapes = new GestionnaireEtapes();
    }

    public void aCommeEntree(Etape etape){this.entree = etape;} // ATTENTION ici est attendu potentiellement plusieurs etapes

    public void aCommeSortie(Etape etape){
        this.sortie = etape;
    }

    public void ajouter(Etape... etapes){
        this.etapes.ajouter(etapes);
    }

    public int nbEtapes(){ return this.etapes.nbEtapes(); }

    public int nbGuichets(){ return this.etapes.nbGuichets(); }


    @Override
    public Iterator<Etape> iterator(){
        return this.etapes.iterator();
    }

    public String toString(){
        String res = "";
        res += this.entree.toString() + "\n";
        for(Etape etape : this.etapes){
            res += etape.toString() + "\n";
        }
        res += this.sortie.toString();
        return res;
    }

    public String toC(){
        return " ";
    }
}
