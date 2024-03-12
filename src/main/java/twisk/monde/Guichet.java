package main.java.twisk.monde;

import main.java.twisk.outils.FabriqueNumero;

public class Guichet extends Etape {

    private int nbJetons;
    private int semaphores;
    public Guichet(String nom){
        super(nom);
        this.nbJetons = 0;
        this.semaphores = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    public Guichet(String nom, int nbJetons){
        super(nom);
        this.nbJetons = nbJetons;
        this.semaphores = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    public Boolean estUnGuichet(){
        return true;
    }

    public Boolean estUneActivite(){
        return false;
    }

    public int getNbJetons(){
        return this.nbJetons;
    }

    public int getNumeroSemaphore(){
        return this.semaphores;
    }

    public String toC(){
        return "";
    }

}
