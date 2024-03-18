package main.java.twisk.simulation;

import main.java.twisk.monde.Monde;
public class Simulation {
    public Simulation(){ }
    public void simuler(Monde monde){
        System.out.println("Vous etes dans une simulation :\n");
        System.out.println(monde.toC());
    }
}
