#include <unistd.h>
#include <stdio.h>
#include <stdbool.h>
#include "def.h"

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