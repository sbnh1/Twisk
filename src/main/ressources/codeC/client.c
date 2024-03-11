#include <stdlib.h>
#include <stdio.h>

#include "def.h"

#define GUICHET_SEM 1
#define num_sem_guichet 2

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
    int sasEntree = 0;
    int guichet1 = 1;
    int guichet2 = 2;
    int activite1 = 3;
    int activite2 = 4;
    int sasSortie = 5;

    entrer(sasEntree);
    delai(6,3);
    transfert(sasEntree, guichet1);
    delai(4,1);

    P(ids, GUICHET_SEM);
    transfert(guichet1, activite1);
    delai(6,2);
    V(ids,GUICHET_SEM);
    transfert(activite1, guichet2);
    delai(5,1);

    P(ids, num_sem_guichet);
    transfert(guichet2, activite2);
    delai(5,1);
    V(ids, num_sem_guichet);
    transfert(activite2, sasSortie);
}

int main(int argc, char** argv){
    simulation(0);
    return 0;
}