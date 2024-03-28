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
        //toutes les etapes doivent être implémenter dans le bon ordre pour que cela marche en mettant sasEntree au tout début et sasSortie à la fin
        int suite = 0;
        StringBuilder string = new StringBuilder();
        string.append("#include <stdlib.h>\n#include <stdio.h>\n\n#include \"def.h\"\n");
        string.append("#define sasEntree 0\n");
        for(int i = 0; i < this.nbEtapes(); i++){
            string.append("#define " + this.etapes.getEtape(i).getNom() + " " + (i + 1) + "\n");
            if(this.etapes.getEtape(i).estUnGuichet()){
                string.append("#define " + this.etapes.getEtape(i).getNom() + "_semaphore " + suite + "\n");
                suite++;
            }
        }
        string.append("#define sasSortie " + (this.nbEtapes() + 1)  + "\n");
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
