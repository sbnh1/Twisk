package main.java;

public class Guichet extends Etape {

    private int nbJetons;
    public Guichet(String nom){
        super(nom);
        this.nbJetons = 0;
    }

    public Guichet(String nom, int nbJetons){
        this.nbJetons = nbJetons;
    }

    public Boolean estUnGuichet(){

    }
}
