#include <stdlib.h>
#include <stdio.h>

#include "def.h"

#define GUICHET_SEM 1

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
    // printf("j'entre dans le guichet\n");
    transfert(guichet, activite);
    // printf("j'entre dans l'activité\n");
    delai(3, 1);
    // printf("j'quitte l'activité\n");
    V(ids, GUICHET_SEM);
    transfert(activite, sasSortie);
    // printf("j'entre dans le sas de sortie\n");
}

int main(int argc, char** argv){
    simulation(0);
    return 0;
}