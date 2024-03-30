package main.java.twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape>{

    private GestionnaireEtapes etapes;
    private Etape entree;
    private Etape sortie;

    /**
     * Constructeur du monde.
     */
    public Monde(){
        this.etapes = new GestionnaireEtapes();
    }

    /**
     * Définit l'étape d'entrée du monde
     * @param etape L'étape d'entrée du monde
     */
    public void aCommeEntree(Etape etape){this.entree = etape;} // ATTENTION ici est attendu potentiellement plusieurs etapes

    /**
     * Définit l'étape de sortie du monde
     * @param etape L'étape de sortie du monde
     */
    public void aCommeSortie(Etape etape){
        this.sortie = etape;
    }

    /**
     * Retourne l'étape d'entrée du monde
     * @return L'étape d'entrée du monde
     */
    public Etape getEntree() {
        return this.entree;
    }

    /**
     * Retourne l'étape de sortie du monde
     * @return L'étape de sortie du monde
     */
    public Etape getSortie() {
        return this.sortie;
    }

    /**
     * Ajoute une ou plusieurs étapes au monde
     * @param etapes Les étapes à ajouter
     */
    public void ajouter(Etape... etapes){
        this.etapes.ajouter(etapes);
    }

    /**
     * Retourne le nombre total d'étapes dans le monde
     * @return Le nombre total d'étapes dans le monde
     */
    public int nbEtapes(){ return this.etapes.nbEtapes(); }

    /**
     * Retourne le nombre total de guichets dans le monde
     * @return Le nombre total de guichets dans le monde
     */
    public int nbGuichets(){ return this.etapes.nbGuichets(); }

    /**
     * Retourne l'étape à l'index spécifié dans le monde
     * @param i L'index de l'étape à retourner
     * @return L'étape à l'index spécifié
     */
    public Etape getEtape(int i){return this.etapes.getEtape(i);}

    /**
     * Retourne un itérateur sur les étapes du monde
     * @return Un itérateur sur les étapes du monde
     */
    @Override
    public Iterator<Etape> iterator(){
        return this.etapes.iterator();
    }

    /**
     * Retourne une représentation textuelle du monde
     * @return Une représentation textuelle du monde
     */
    public String toString(){
        String res = "";
        res += this.entree.toString() + "\n";
        for(Etape etape : this.etapes){
            res += etape.toString() + "\n";
        }
        res += this.sortie.toString();
        return res;
    }

    /**
     * Retourne une représentation en langage C du monde
     * @return Une représentation en langage C du monde
     */

    public String toC(){
        //toutes les etapes doivent être implémenter dans le bon ordre pour que cela marche en mettant sasEntree au tout début et sasSortie à la fin
        int suite = 0;
        StringBuilder string = new StringBuilder();
        string.append("#include <stdlib.h>\n#include <stdio.h>\n\n#include \"def.h\"\n");
        for(int i = 0; i < this.nbEtapes(); i++){
            string.append("#define " + this.etapes.getEtape(i).getNom() + " " + i + "\n");
            if(this.etapes.getEtape(i).estUnGuichet()){
                string.append("#define " + this.etapes.getEtape(i).getNom() + "_semaphore " + suite + "\n");
                suite++;
            }
        }
        //jusqu'ici j'ai tout les #include #define
        string.append("void simulation(int ids){\n");
        for(int i = 0; i < this.nbEtapes(); i++){
            string.append(this.etapes.getEtape(i).toC());
            //pour ne pas faire 2 fois le toC de l'activité restreinte (on le skip donc ici et on l'apelle dans toC() de Guichet)
            if(this.etapes.getEtape(i).estUnGuichet()){
                i++;
            }
        }
        string.append("}");
        return string.toString();
    }
}
