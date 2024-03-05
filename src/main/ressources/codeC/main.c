#include "def.h"

int main(int argc, char** argv) {
    int nbEtapes = 3;
    int nbGuichets = 0;
    int nbClients = 5;
    int* tabJetonsGuichet = NULL;

    int* pids = start_simulation(nbEtapes, nbGuichets, nbClients, tabJetonsGuichet);

    printf("clients : ");
    for (int i = 0; i < nbClients; ++i)
        printf("%d, ", pids[i]);

    printf("\n");
    
    nettoyage();
    return 0;
}