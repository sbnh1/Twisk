package twisk.monde;

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

    public ArrayList<Etape> getEtapes(){
        return this.etapes;
    }

    @Override
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

    public String toString(){
        String res = null;

        for(Etape etape : this.etapes){
            res += etape.toString() + "\n";
        }

        return res;
    }

}
