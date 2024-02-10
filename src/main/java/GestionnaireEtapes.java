package main.java;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape>{

    private ArrayList<Etape> etapes;

    public GestionnaireEtapes(){
        this.etapes = new ArrayList<Etape>();
    }

    public void ajouter(Etape... etapes ){
        this.etapes.add(etapes);
    }

    public Iterator<Etape> iterator(){
    }
}
