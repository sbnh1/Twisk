#include <stdlib.h>
#include <stdio.h>

#include "def.h"

#define sasEntree  0
#define guichet1  1
#define activite1  2
#define guichet2  3
#define activite2  4
#define sasSortie  5
#define GUICHET_SEM 6
#define num_sem_guichet 7

/*
void simulation(int ids){

    int sasEntree = 0;
    int guichet = 1;
    int activite = 2;
    int sasSortie = 3;

    entrer(sasEntree);
    // printf("j'entre dans le sas d'entrée\n");
    delai(6, 3);
    // printf("je quitte le sas d'entrée\n");
    transfert(sasEntree, guichet);
    P(ids, GUICHET_SEM);

    transfert(guichet, activite);

    delai(3, 1);
    // printf("j'quitte l'activité\n");
    V(ids, GUICHET_SEM);
    transfert(activite, sasSortie);
    // printf("j'entre dans le sas de sortie\n");
}*/

void simulation(int ids){
    entrer(sasEntree);
    delai(6,3);
    transfert(sasEntree, guichet1);


    delai(4,1);
    P(ids, GUICHET_SEM);
    transfert(guichet1, activite1);

    delai(6,2);
    transfert(activite1, guichet2);
    V(ids,GUICHET_SEM);

    delai(5,1);
    P(ids, num_sem_guichet);
    transfert(guichet2, activite2);

    delai(5,1);
    transfert(activite2, sasSortie);

    V(ids, num_sem_guichet);
}

int main(int argc, char** argv){
    simulation(0);
    return 0;
}