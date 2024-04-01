package main.java.twisk;

import main.java.twisk.outils.KitC;
import main.java.twisk.simulation.Simulation;
import main.java.twisk.monde.*;

public class ClientTwisk {
    public static void main(String[] args) {
        Monde monde1 = creerMonde1();
        Monde monde2 = creerMonde2();
        Monde monde3 = creerMonde3();
        System.out.println(monde1.toC());
        Simulation simulation = new Simulation();
        simulation.simuler(monde1);
        //simulation.simuler(monde2);
        //simulation.simuler(monde3);

    }

    private static Monde creerMonde1(){
        //création du monde
        Monde monde1 = new Monde();
        //création des étapes
        Etape guichet1 = new Guichet("Guichet1", 5);
        Etape guichet2 = new Guichet("Guichet2", 5);
        //création des activitées
        Etape activite1 = new ActiviteRestreinte("Tobogan");
        Etape activite2 = new ActiviteRestreinte("Piscine", 5, 2);
        //création des entrées et sorties du monde
        Etape entree = new SasEntree();
        Etape sortie = new SasSortie();
        //lien entre les guichets et les activitéess
        entree.ajouterSuccesseur(guichet1);
        guichet1.ajouterSuccesseur(activite1);
        activite1.ajouterSuccesseur(guichet2);
        guichet2.ajouterSuccesseur(activite2);
        activite2.ajouterSuccesseur(sortie);


        //Initialisation du monde.
        monde1.aCommeEntree(entree);
        monde1.aCommeSortie(sortie);
        monde1.ajouter(entree, guichet1, activite1, guichet2, activite2, sortie);
        return monde1;
    }

    private static Monde creerMonde2(){
        Monde monde2 = new Monde();
        Etape etape1 = new Guichet("Guichet1", 10);
        Etape etape2 = new Guichet("Guichet2", 10);
        Etape activite1 = new Activite("Tobogan", 5, 2);
        Etape activite2 =  new Activite("Pingpong", 10, 2);
        Etape activite3 =  new Activite("Parc", 10, 4);
        Etape entree = new SasEntree();
        Etape sortie = new SasSortie();
        //lien
        entree.ajouterSuccesseur(etape1);
        etape1.ajouterSuccesseur(activite1, activite2);
        activite1.ajouterSuccesseur(etape2);
        activite2.ajouterSuccesseur(etape2);
        etape2.ajouterSuccesseur(activite3);
        activite3.ajouterSuccesseur(sortie);
        //initialisation
        monde2.aCommeEntree(entree);
        monde2.aCommeSortie(sortie);
        monde2.ajouter(etape1,activite1,activite2,etape2, activite3);
        return monde2;
    }

    private static Monde creerMonde3(){
        Monde monde = new Monde();
        Etape e1 = new Activite("Toboggan",3,2);
        Etape e2 = new Activite("BacASable",5,1);
        Etape e3 = new Activite("Balancoire",4,2);
        SasSortie sasSortie = new SasSortie();
        SasEntree sasEntree = new SasEntree();
        sasEntree.ajouterSuccesseur(e1);
        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(sasSortie);

        monde.aCommeEntree(sasEntree);
        monde.aCommeSortie(sasSortie);
        monde.ajouter(sasEntree, e1,e2,e3, sasSortie);
        return monde;
    }
}
