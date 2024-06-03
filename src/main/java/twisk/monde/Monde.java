package twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape>{

    private GestionnaireEtapes etapes;
    private Etape entree;
    private Etape sortie;
    private int choixLoi;

    /**
     * Constructeur du monde.
     */
    public Monde(){
        this.entree = new SasEntree();
        this.sortie = new SasSortie();
        this.etapes = new GestionnaireEtapes();
        this.etapes.ajouter(this.entree, this.sortie);
        this.choixLoi = 1;
    }

    /**
     * Définit l'étape d'entrée du monde
     * @param etapes Les etapes d'entrée du monde
     */
    public void aCommeEntree(Etape... etapes){
        this.entree.ajouterSuccesseur(etapes);
    }

    /**
     * Définit l'étape de sortie du monde
     * @param etapes Les étapes de sortie du monde
     */
    public void aCommeSortie(Etape... etapes){
        for (Etape etape : etapes){
            etape.ajouterSuccesseur(this.sortie);
        }
    }

    /**
     * Retourne l'étape d'entrée du monde
     * @return L'étape d'entrée du monde
     */
    public String getEntree() {
        return this.entree.getNom();
    }

    /**
     * Retourne l'étape de sortie du monde
     * @return L'étape de sortie du monde
     */
    public String getSortie() {
        return this.sortie.getNom();
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
     * Défini la loi à utiliser
     * @param choixLoi la loi choisie
     */
    public void setChoixLoi(int choixLoi){
        this.choixLoi = choixLoi;
    }

    /**
     * Renvoie la loi définie
     * @return la loi définie
     */
    public int getChoixLoi(){
        return this.choixLoi;
    }


    /**
     * Methode qui genere le code C de la fonction delaiUniforme
     * @return Le code C de la fonction
     */
    private String toCUniforme(){

        StringBuilder s = new StringBuilder();
        s.append("void delaiUniforme(int temps, int delta){\n");
        s.append("int bi, bs;\n");
        s.append("int n, nbSec;\n");
        s.append("bi = temps - delta;\n");
        s.append("if (bi < 0) bi = 0 ;\n");
        s.append("bs = temps + delta ;\n");
        s.append("n = bs - bi + 1 ;\n");
        s.append("nbSec = (rand()/ (float)RAND_MAX) * n ;\n");
        s.append("nbSec += bi ;\n");
        s.append("usleep(nbSec * 500000);\n");
        s.append("}\n");
        return s.toString();
    }


    /**
     * Methode qui genere le code C de la fonction delaiGauss
     * @return Le code C de la fonction
     */
    private String toCGauss(){

        StringBuilder s = new StringBuilder();
        s.append("void delaiGauss(double moyenne, double ecartype){\n");
        s.append("double U1 = (double)rand()/RAND_MAX;\n");
        s.append("double U2 = (double)rand()/RAND_MAX;\n");
        s.append("double X = (sqrt(-2*log(U1))) * (cos(2*PI*U2)*ecartype) + moyenne; \n");
        s.append("usleep(X * 500000);\n");
        s.append("}\n");
        return s.toString();
    }


    /**
     * Methode qui genere le code C de la fonction delaiExponentiel
     * @return Le code C de la fonction
     */
    private String toCExpo(){

        StringBuilder s = new StringBuilder();
        s.append("void delaiExponentiel(double lambda){\n");
        s.append("double U = (double)rand()/RAND_MAX;\n");
        s.append("double X = -log(U)/lambda;\n");
        s.append("usleep(X * 500000);\n");
        s.append("}\n");
        return s.toString();
    }

    /**
     * Retourne une représentation en C du monde
     * @return Une représentation en C du monde
     */
    public String toC(){
        StringBuilder string = new StringBuilder();
        string.append("#include <stdlib.h>\n#include <stdio.h>\n#include \"def.h\"\n#include <math.h>\n#include <time.h>\n#include <unistd.h>\n\n");
        for(int i = 0; i < this.nbEtapes(); i++){
            string.append("#define " + this.etapes.getEtape(i).getNom() + " " + this.etapes.getEtape(i).getId() + "\n");
            if(this.etapes.getEtape(i).estUnGuichet()){
                string.append("#define " + this.etapes.getEtape(i).getNom() + "_semaphore " + ((Guichet)this.etapes.getEtape(i)).getNumeroSemaphore() + "\n");
            }
        }
        string.append("#define PI 3.14159265358979323846\n");
        string.append(this.toCUniforme());
        string.append(this.toCGauss());
        string.append(this.toCExpo());

        string.append("\nvoid simulation(int ids){\n");
        string.append("int loi = " + this.choixLoi + ";\n");
        string.append("srand(getpid());\n");
        string.append(this.etapes.getEtape(0).toC());

        string.append("}");
        return string.toString();
    }
}
