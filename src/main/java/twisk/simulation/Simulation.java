package main.java.twisk.simulation;

import main.java.twisk.monde.Guichet;
import main.java.twisk.monde.Monde;
import main.java.twisk.outils.KitC;

public class Simulation {
    private KitC kitC;
    private int nbClient;

    /**
     * constructeur d'une simulation
     */
    public Simulation(){
    }

    /**
     * Méthode qui permet de démarrer la simulation avec les paramètres suivant
     * @param nbEtapes le nombre total d'étape de la simulation
     * @param nbGuichets le nombre total de guichet de la simulation
     * @param nbClients le nombre total de client dans la simulation
     * @param tabJetonsGuichets un tableau d'entier avec le nombre de jeton de chaque guichet dans l'ordre
     * @return un tableau d'entier qui représente la simulation de facon linéaire
     */
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);

    /**
     * Méthode qui retourne les positions actuelles des clients dans la simulation
     * @param nbEtapes le nombre total d'étape dans la simulation
     * @param nbClients le nombre total de client dans la simulation
     * @return un tableau d'entier qui représente la position actuelle des clients de la simulation
     */
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);

    /**
     * Méthode qui effectue le nettoyage des ressources utilisé lors de la simulation
     * à utiliser a la fin de la simulation
     */
    public native void nettoyage();
    /**
     * Défini le nombre de client du monde
     * @param nbClient le nombre de client dans le monde
     */
    public void setNbClient(int nbClient){
        this.nbClient = nbClient;
    }

    /**
     * Retourne la simulation du monde
     * @param monde le monde a simuler
     */
    public void simuler(Monde monde){
        this.kitC = new KitC();
        this.kitC.creerEnvironnement();
        this.kitC.creerFichier(monde.toC());
        this.kitC.compiler();
        this.kitC.construireLaBibliotheque();
        System.load("/tmp/twisk/libTwisk.so");

        int[] tabJetonsGuichet = new int[monde.nbGuichets()];
        //initialisation du nombre de client dans ClientTwisk par la commande : simulation.setNbClient(20);
        int nbClient = this.nbClient;
        for(int i = 0; i < monde.nbEtapes(); i++){
            if(monde.getEtape(i).estUnGuichet()){
                tabJetonsGuichet[((Guichet)monde.getEtape(i)).getNumeroSemaphore()-1] = monde.getEtape(i).getNbJetons();

            }
        }

        int[] pids = start_simulation(monde.nbEtapes(), monde.nbGuichets(), nbClient, tabJetonsGuichet);

        System.out.println("ids clients : ");
        for(int i = 0; i < nbClient; i++){
            System.out.print(pids[i] + " ");
        }
        System.out.println("\n");

        System.out.println("\nposition des clients : \n");

        int[] posClients;
        boolean fin = false;

        while(fin == false){
            posClients = ou_sont_les_clients(monde.nbEtapes(), nbClient);
            for(int i = 0; i < ((nbClient + 1) * monde.nbEtapes()); i+=(nbClient + 1)){
                System.out.print(monde.getEtape(i/(nbClient+1)).getNom() + ": ");
                for(int j = i; j < (i + posClients[i] + 1); j++){
                    System.out.print(posClients[j] + " ");
                }
                if(posClients[((nbClient+1)*(monde.nbEtapes()-1))] == nbClient && i == ((nbClient+1)*(monde.nbEtapes()-1))){
                    fin = true;
                }
                System.out.println("\n");
            }
            System.out.println("\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        posClients = null;
        nettoyage();
    }
}