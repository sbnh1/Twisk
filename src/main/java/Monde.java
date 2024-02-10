package main.java;

import java.util.ArrayList;

public class Monde implements Iterable<Etape>{

    private ArrayList<Etape> etapes;

    public Monde(){
        this.etapes = new ArrayList<Etape>();
    }
    public void aCommeEntree(Etape... etapes){
    }

    public void aCommeSortie(Etape... etapes){

    }

    public void ajouter(Etape... etapes){
        this.etapes.add(etapes);
    }

    public int nbEtapes(){

    }

    public int nbGuichets(){

    }
}
