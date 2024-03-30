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

        Simulation simulation = new Simulation();
        simulation.simuler(monde1);
        //simulation.simuler(monde2);

    }

    private static Monde creerMonde1(){
        //création du monde
        Monde monde1 = new Monde();
        //création des étapes
        Etape guichet1 = new Guichet("Guichet1");
        Etape guichet2 = new Guichet("Guichet2");
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

}
