package main.java.twisk.simulation;

import main.java.twisk.monde.Monde;
import main.java.twisk.outils.KitC;

public class Simulation {
    private KitC kitC;
    public Simulation(){
    }
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichets);
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);
    public native void nettoyage();
    public void simuler(Monde monde){
        this.kitC = new KitC();
        this.kitC.creerEnvironnement();
        this.kitC.creerFichier(monde.toC());
        this.kitC.compiler();
        this.kitC.construireLaBibliotheque();
        System.load("/tmp/twisk/libTwisk.so");

        int[] tabJetonsGuichet = new int[monde.nbGuichets()];
        //comment choisir le nombre de client, ou est ce qu'on le précise ?????
        //pour l'instant codé en dure dans le code en dessous
        int nbClient = 20;
        for(int i = 0; i < monde.nbEtapes(); i++){
            if(monde.getEtape(i).estUnGuichet()){
                tabJetonsGuichet[monde.getEtape(i).getId() - 1] = monde.getEtape(i).getNbJetons();
            }
        }

        int[] pids = start_simulation(monde.nbEtapes(), monde.nbGuichets(), nbClient, tabJetonsGuichet);

        System.out.println("ids clients : ");
        for(int i = 0; i < nbClient; i++){
            System.out.println(pids[i]);
        }
        System.out.println("\n");

        System.out.println("\nposition des clients : \n");

        int[] posClients;
        boolean fin = false;

        while(fin == false){
            posClients = ou_sont_les_clients(monde.nbEtapes(), nbClient);
            for(int i = 0; i < (nbClient + 1) * monde.nbEtapes(); i+=(nbClient + 1)){
                System.out.println("etape " + i/(nbClient+1) + ":");
                for(int j = i; j < i + posClients[i] + 1; j++){
                    System.out.println(posClients[j] + " ");
                }
                if(posClients[((nbClient+1)*(monde.nbEtapes()-1))] == nbClient && i == ((nbClient+1)*(monde.nbEtapes()-1))){
                    fin = true;
                }
                System.out.println("\n");
            }
            System.out.println("\n");
            //pas de free en java ? je met le tableau a null pour que le garbage collector s'en occupe
            posClients = null;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        nettoyage();
        //pas de return 0
    }
}
/*

int main(int argc, char** argv) {
    int nbEtapes = 6;
    int nbGuichets = 2;
    int nbClients = 25;
    int* tabJetonsGuichet = malloc(sizeof(int)+nbGuichets);

    tabJetonsGuichet[0] = 6;
    tabJetonsGuichet[1] = 2;

    int* pids = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);

    printf("ids clients : ");
    for (int i = 0; i < nbClients; ++i)
        printf("%d, ", pids[i]);
    printf("\n");

    printf("\nposition des clients : \n");

    int* posClients;
    bool fin = false;

    while(fin == false){
        posClients = ou_sont_les_clients(nbEtapes, nbClients);
        for (int i = 0; i < (nbClients + 1) * nbEtapes; i += (nbClients + 1)) {
            printf("etape %d: ", i/(nbClients+1));
            for (int j = i; j < i + posClients[i] + 1; j++){
                 printf("%d ", posClients[j]);
            }
            if(posClients[((nbClients+1)*(nbEtapes-1))] == nbClients && i == ((nbClients+1)*(nbEtapes-1))){
                fin = true;
            }
            printf("\n");
        }
        printf("\n");

        free(posClients);

        sleep(1);
    }

    nettoyage();
    return 0;
}
 */