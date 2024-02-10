package main.java;

import java.util.Iterator;

public class Monde implements Iterable<Etape>{

    private GestionnaireEtapes etapes;

    public Monde(){
        this.etapes = new GestionnaireEtapes();
    }
    public void aCommeEntree(Etape... etapes){
    }

    public void aCommeSortie(Etape... etapes){

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
}
