#include <stdlib.h>
#include <stdio.h>

#include "def.h"

void simulation(int ids){

    int sasEntree = 0;
    int activite = 1;
    int sasSortie = 2;

    entrer(sasEntree);
    printf("j'entre dans le sas d'entrée");
    delai(1, 0);
    printf("je quitte le sas d'entrée");
    transfert(sasEntree, activite);
    printf("j'entre dans l'activité");
    delai(1, 0);
    printf("j'quitte l'activité");
    transfert(activite, sasSortie);
    printf("j'entre dans le sas de sortie");
}

int main(int argc, char** argv){
    simulation(0);
    return 0;
}