package test.java.twisk.monde;

import main.java.twisk.monde.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testMonde {
    @Test
    void testNbEtapes(){
        Monde monde = new Monde();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet1 = new Guichet("Guichet1");
        Guichet guichet2 = new Guichet("Guichet2");
        monde.ajouter(etape1, etape2, guichet1, guichet2);
        assertEquals(4, monde.nbEtapes());
    }
    @Test
    void testNbEtapes0(){
        Monde monde = new Monde();
        assertEquals(0, monde.nbEtapes());
    }
    @Test
    void testNbGuichet0(){
        Monde monde = new Monde();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        monde.ajouter(etape1, etape2);
        assertEquals(0, monde.nbGuichets());
    }
    @Test
    void testNbGuichet(){
        Monde monde = new Monde();
        Activite etape1 = new Activite("Activite1");
        Guichet guichet1 = new Guichet("Guichet1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet2 = new Guichet("Guichet2");
        monde.ajouter(etape1, etape2, guichet1, guichet2);
        assertEquals(2, monde.nbGuichets());
    }

    @Test
    void testToC() {
        // Création des étapes du monde
        SasEntree sasEntree = new SasEntree();
        Guichet guichet = new Guichet("Guichet");
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("ActiviteRestreinte");
        SasSortie sasSortie = new SasSortie();

        // Ajout des étapes au monde
        Monde monde = new Monde();
        monde.aCommeEntree(sasEntree);
        monde.ajouter(sasEntree, guichet, activiteRestreinte,sasSortie);
        monde.aCommeSortie(sasSortie);
        sasEntree.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(activiteRestreinte);
        activiteRestreinte.ajouterSuccesseur(sasSortie);



        // Construction de la chaîne attendue
        StringBuilder expected = new StringBuilder();
        expected.append("#include <stdlib.h>\n#include <stdio.h>\n\n#include \"def.h\"\n");
        expected.append("#define SasEntree 0\n");
        expected.append("#define Guichet 1\n");
        expected.append("#define Guichet_semaphore 0\n");
        expected.append("#define ActiviteRestreinte 2\n");
        expected.append("#define SasSortie 3\n");
        expected.append("void simulation(int ids){\n");
        expected.append("    entrer(SasEntree);\n" +
                "    delai(6,3);\n" +
                "    transfert(SasEntree, Guichet);\n" +
                "    delai(4,1);\n" +
                "    P(ids, Guichet_semaphore);\n" +
                "    transfert(Guichet, ActiviteRestreinte);\n" +
                "    delai(6,2);\n" +
                "    transfert(ActiviteRestreinte, SasSortie);\n" +
                "    V(ids, Guichet_semaphore);\n}");
        assertEquals(expected.toString(), monde.toC().toString());
    }
}
