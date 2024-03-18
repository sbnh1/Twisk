package main.java.twisk.simulation;

import main.java.twisk.monde.*;

public class Simulation {
    public Simulation(){ }
    public void simuler(Monde monde){
        System.out.println("Vous etes dans une simulation :\n");

        //création des étapes du monde
        SasEntree sasEntree = new SasEntree("SasEntree");
        Guichet guichet = new Guichet("Guichet");
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("ActiviteRestreinte");
        SasSortie sasSortie = new SasSortie("SasSortie");

        //ajout des étapes au monde
        monde.aCommeEntree(sasEntree);
        monde.ajouter(guichet, activiteRestreinte);
        monde.aCommeSortie(sasSortie);
        sasEntree.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(activiteRestreinte);
        activiteRestreinte.ajouterSuccesseur(sasSortie);

        System.out.println(monde.toC());
    }
}
