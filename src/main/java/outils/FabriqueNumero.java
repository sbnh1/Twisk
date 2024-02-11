package main.java.outils;

public class FabriqueNumero {
    // La classe est testé dans testEtape
    private static FabriqueNumero instance;
    private int compteur;

    private FabriqueNumero() {
        this.compteur = 0;
    }

    public static FabriqueNumero getInstance() {
        if (instance == null) {
            instance = new FabriqueNumero();
        }
        return instance;
    }

    public int getNumeroEtape() {
        return this.compteur++;
    }

    public void reset() {
        this.compteur = 0;
    }
}
