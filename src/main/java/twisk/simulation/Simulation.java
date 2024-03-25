package main.java.twisk.simulation;

import main.java.twisk.monde.*;
import main.java.twisk.outils.KitC;

public class Simulation {
    private KitC kitC;
    public Simulation(KitC kitC){
        this.kitC = kitC;
        this.kitC.creerEnvironnement();
    }
    public void simuler(Monde monde){
        System.out.println("Vous etes dans une simulation\n");
        this.kitC.creerFichier(monde.toC());
        this.kitC.compiler();
        this.kitC.construireLaBibliotheque();
    }
}
