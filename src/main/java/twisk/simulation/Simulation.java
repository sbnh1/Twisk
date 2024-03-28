package main.java.twisk.simulation;

import main.java.twisk.monde.*;
import main.java.twisk.outils.KitC;

public class Simulation {
    private KitC kitC;
    public Simulation(KitC kitC){
        this.kitC = kitC;
        this.kitC.creerEnvironnement();
    }
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();
    public void simuler(Monde monde){
        this.kitC.creerFichier(monde.toC());
        this.kitC.compiler();
        this.kitC.construireLaBibliotheque();
    }
}
