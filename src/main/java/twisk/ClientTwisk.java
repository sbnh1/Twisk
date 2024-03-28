package main.java.twisk;

import main.java.twisk.outils.KitC;
import main.java.twisk.simulation.Simulation;
import main.java.twisk.monde.*;

public class ClientTwisk {
    public static void main(String[] args) {
        //Création d'un premier monde
        Monde monde1 = creerMonde1();


        //Création d'un second monde
        Monde monde2 = creerMonde2();

        Simulation simulation = new Simulation(new KitC());
        simulation.simuler(monde1);
        simulation.simuler(monde2);

    }

    private static Monde creerMonde1(){
        //création du monde
        Monde monde1 = new Monde();
        //création des étapes
        Etape etape1 = new Guichet("Guichet1");
        Etape etape2 = new Guichet("Guichet2");
        //création des activitées
        Etape activite1 = new Activite("Activite1");
        Etape activite2 = new Activite("Activité2", 10, 2);
        //lien entre les guichets et les activitéess
        etape1.ajouterSuccesseur(activite1);
        etape2.ajouterSuccesseur(activite2);
        //création des entrées et sorties du monde
        Etape entree = new SasEntree("Entree");
        Etape sortie = new SasSortie("Sortie");

        //Initialisation du monde.
        monde1.aCommeEntree(entree);
        monde1.aCommeSortie(sortie);
        monde1.ajouter(etape1,etape2);
        return monde1;
    }

    private static Monde creerMonde2(){
        Monde monde2 = new Monde();
        Etape etape1 = new Guichet("Guichet1", 10);
        Etape etape2 = new Guichet("Guichet2", 10);
        Etape activite1 = new Activite("Tobogan", 5, 2);
        Etape activite2 =  new Activite("Pingpong", 10, 2);
        Etape activite3 =  new Activite("Parc", 10, 4);
        Etape entree = new SasEntree("Entrée");
        Etape sortie = new SasSortie("Sortie");
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

}
