package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape> {

    private ArrayList<Etape> etapes;

    /**
     * Constructeur du gestionnaire d'étapes
     */
    public GestionnaireEtapes() {
        this.etapes = new ArrayList<Etape>();
    }

    /**
     * Ajoute une ou plusieurs étapes au gestionnaire d'étapes
     * @param etapes Les étapes à ajouter
     */
    public void ajouter(Etape... etapes) {
        this.etapes.addAll(Arrays.asList(etapes));
    }

    /**
     * Retourne le nombre total d'étapes dans le gestionnaire
     * @return Le nombre total d'étapes dans le gestionnaire
     */
    public int nbEtapes() {
        return etapes.size();
    }

    /**
     * Retourne le nombre total de guichets dans le gestionnaire
     * @return Le nombre total de guichets dans le gestionnaire
     */
    public int nbGuichets() {
        int countGuichets = 0;
        for (Etape etape : etapes) {
            if (etape.estUnGuichet()) {
                countGuichets++;
            }
        }
        return countGuichets;
    }

    /**
     * Retourne la liste des étapes dans le gestionnaire
     * @return La liste des étapes dans le gestionnaire
     */
    public ArrayList<Etape> getEtapes(){
        return this.etapes;
    }

    /**
     * Retourne l'étape à l'index spécifié dans le gestionnaire
     * @param n L'index de l'étape à retourner
     * @return L'étape à l'index spécifié, ou null si l'index est invalide
     */
    public Etape getEtape(int n){
        if (n >= 0 && n < this.etapes.size()) {
            return this.etapes.get(n);
        } else {
            return null;
        }
    }

    /**
     * Retourne un itérateur sur les étapes du gestionnaire
     * @return Un itérateur sur les étapes du gestionnaire
     */
    @Override
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

    /**
     * Retourne une représentation textuelle du gestionnaire d'étapes
     * @return Une représentation textuelle du gestionnaire d'étapes
     */
    public String toString(){
        String res = null;

        for(Etape etape : this.etapes){
            res += etape.toString() + "\n";
        }

        return res;
    }
}
