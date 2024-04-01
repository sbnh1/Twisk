package main.java.twisk;

import main.java.twisk.outils.FabriqueNumero;
import main.java.twisk.outils.KitC;
import main.java.twisk.simulation.Simulation;
import main.java.twisk.monde.*;

public class ClientTwisk {
    public static void main(String[] args) {
        Monde monde1 = creerMonde1();
        Monde monde2 = creerMonde2();
        Monde monde3 = creerMonde3();

        Simulation simulation = new Simulation();
        simulation.setNbClient(20);
        simulation.simuler(monde1);
        //simulation.simuler(monde2);
        //simulation.simuler(monde3);

    }

    /**
     * Creation d'un premier monde
     * @return Le Monde monde1
     */
    private static Monde creerMonde1(){
        //création du monde
        Monde monde1 = new Monde();
        //création des étapes
        Etape entree = new SasEntree();
        Etape guichet1 = new Guichet("Guichet1", 5);
        Etape guichet2 = new Guichet("Guichet2", 5);
        Etape activite1 = new ActiviteRestreinte("Tobogan", 5,2);
        Etape activite2 = new ActiviteRestreinte("Piscine", 5, 2);
        Etape sortie = new SasSortie();
        //lien
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

    /**
     * Creation d'un second monde
     * @return Le Monde monde2
     */
    private static Monde creerMonde2(){
        FabriqueNumero.getInstance().reset();
        FabriqueNumero.getInstance().resetNumeroSemaphore();
        Monde monde2 = new Monde();
        Etape entree = new SasEntree();

        Etape etape1 = new Guichet("Guichet1", 10);
        Etape etape2 = new Guichet("Guichet2", 10);

        Etape activite1 = new ActiviteRestreinte("Tobogan", 5, 2);
        Etape activite3 =  new ActiviteRestreinte("Parc", 10, 4);

        Etape sortie = new SasSortie();
        //lien
        entree.ajouterSuccesseur(etape1);
        etape1.ajouterSuccesseur(activite1);
        activite1.ajouterSuccesseur(etape2);
        etape2.ajouterSuccesseur(activite3);
        activite3.ajouterSuccesseur(sortie);
        //initialisation
        monde2.aCommeEntree(entree);
        monde2.aCommeSortie(sortie);
        monde2.ajouter(entree, etape1,activite1,etape2, activite3, sortie);
        return monde2;
    }

    /**
     * Creation d'un troisième monde
     * @return Le Monde monde3
     */
    private static Monde creerMonde3(){
        FabriqueNumero.getInstance().reset();
        FabriqueNumero.getInstance().resetNumeroSemaphore();
        Monde monde3 = new Monde();
        SasEntree sasEntree = new SasEntree();
        Etape e1 = new Activite("Toboggan",3,1);
        Etape e2 = new Activite("BacASable",5,1);
        Etape e3 = new Activite("Balancoire",4,2);
        SasSortie sasSortie = new SasSortie();

        sasEntree.ajouterSuccesseur(e1);
        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(sasSortie);

        monde3.aCommeEntree(sasEntree);
        monde3.aCommeSortie(sasSortie);
        monde3.ajouter(sasEntree, e1,e2,e3, sasSortie);
        return monde3;
    }
}
