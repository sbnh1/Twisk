package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape> {

    private ArrayList<Etape> etapes;

    public GestionnaireEtapes() {
        this.etapes = new ArrayList<Etape>();
    }

    public void ajouter(Etape... etapes) {
        this.etapes.addAll(Arrays.asList(etapes));
    }

    public int nbEtapes() {
        return etapes.size();
    }
    public int nbGuichets() {
        int countGuichets = 0;
        for (Etape etape : etapes) {
            if (etape.estUnGuichet()) {
                countGuichets++;
            }
        }
        return countGuichets;
    }

    @Override
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }


}
