package twisk.simulation;

import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.mondeIG.SujetObserve;
import twisk.outils.FabriqueIdlib;
import twisk.outils.KitC;

import java.io.IOException;
import java.util.Arrays;

public class Simulation extends SujetObserve {
    private KitC kitC;
    private int nbClient;
    private int[] pids;
    private GestionnaireClients gestionnaireClients;

    /**
     * Constructeur d'une simulation
     */
    public Simulation(){
        this.gestionnaireClients = new GestionnaireClients();
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
     * @param nbClients le nombre de client dans le monde
     */
    public void setNbClients(int nbClients){
        this.nbClient = nbClients;
    }

    /**
     * Affiche le monde sur la sortie standard
     * @param monde le monde affiché
     */
    public void afficherMonde(Monde monde){
        for(int i = 0; i < monde.nbEtapes(); i++){
            System.out.println(monde.getEtape(i).toString());
        }
        System.out.println();
    }

    /**
     * Methode qui renvoie le GestionnaireClient de la Simulation
     * @return Le GestionnaireClient
     */
    public GestionnaireClients getGestionnaire(){
        return this.gestionnaireClients;
    }

    /**
     * Retourne la simulation du monde
     * @param monde le monde a simuler
     */
    public void simuler(Monde monde){
        //afficherMonde(monde);
        this.kitC = new KitC();
        this.kitC.creerEnvironnement();
        this.kitC.creerFichier(monde.toC());
        this.kitC.compiler();
        this.kitC.construireLaBibliotheque();
        System.load("/tmp/twisk/libTwisk" + FabriqueIdlib.getInstance().getCurrentId() + ".so");

        int[] tabJetonsGuichet = new int[monde.nbGuichets()];
        //initialisation du nombre de clients dans ClientTwisk par la commande : simulation.setNbClient(20);
        System.out.println(monde.nbEtapes());
        int nbClient = this.nbClient;
        for(int i = 0; i < monde.nbEtapes(); i++){
            if(monde.getEtape(i).estUnGuichet()){
                tabJetonsGuichet[((Guichet)monde.getEtape(i)).getNumeroSemaphore()-1] = monde.getEtape(i).getNbJetons();

            }
        }

        this.pids = start_simulation(monde.nbEtapes(), monde.nbGuichets(), nbClient, tabJetonsGuichet);



        this.gestionnaireClients.setClients(pids);
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
            System.out.println(Arrays.toString(posClients));
            for(int i = 0; i < ((nbClient + 1) * monde.nbEtapes()); i+=(nbClient + 1)){
                System.out.print("étape " + i/(nbClient+1) + "  (" + monde.getEtape(i/(nbClient+1)).getNom() + ")  " + posClients[i] + " clients : ");
                for(int j = i+1; j < (i + posClients[i] + 1); j++){
                    this.gestionnaireClients.allerA(posClients[j], monde.getEtape(i/(nbClient+1)), j % nbClient);
                    System.out.print(posClients[j] + " ");
                }
                if(posClients[((nbClient+1)*(monde.nbEtapes()- monde.nbEtapes() + 1))] == nbClient && i == ((nbClient+1)*(monde.nbEtapes()-1))){
                    fin = true;
                }
                System.out.println();
            }
            System.out.println("\n");;
            this.notifierObservateur();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        posClients = null;
        nettoyage();
    }

    public void tuerProcessus(){

        KitC kitC = new KitC();
        try {
            kitC.tuerProcessus(this.pids);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}