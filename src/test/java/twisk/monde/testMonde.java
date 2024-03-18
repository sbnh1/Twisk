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
        SasEntree sasEntree = new SasEntree("SasEntree");
        Guichet guichet = new Guichet("Guichet");
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("ActiviteRestreinte");
        SasSortie sasSortie = new SasSortie("SasSortie");

        // Ajout des étapes au monde
        Monde monde = new Monde();
        monde.aCommeEntree(sasEntree);
        monde.ajouter(guichet, activiteRestreinte);
        monde.aCommeSortie(sasSortie);
        sasEntree.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(activiteRestreinte);
        activiteRestreinte.ajouterSuccesseur(sasSortie);



        // Construction de la chaîne attendue
        StringBuilder expected = new StringBuilder();
        expected.append("#include <stdlib.h>\n#include <stdio.h>\n\n#include def.h\n");
        expected.append("#define SasEntree 0\n");
        expected.append("#define Guichet 1\n");
        expected.append("#define guichet_semaphore 0\n");
        expected.append("#define ActiviteRestreinte 2\n");
        expected.append("#define sasSortie 3\n");
        expected.append("void simulation(int ids){\n");
        expected.append("    delai(4,1);\n" +
                "    P(ids, guichet_semaphore1);\n" +
                "    transfert(Guichet, ActiviteRestreinte);\n" +
                "    delai(6,2);\n" +
                "    transfert(ActiviteRestreinte,SasSortie: 0: );\n" +
                "    V(ids, guichet_semaphore1);\n");
        expected.append("int main(int argc, char** argv){\n" + "    simulation(0);\n" + "    return 0;\n" + "}\n");

        //System.out.println(expected.toString());
        //System.out.println(monde.toC().toString());
        assertEquals(expected.toString(), monde.toC().toString());
    }
}
