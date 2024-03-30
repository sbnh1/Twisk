package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.SasEntree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class testSasEntree {
    @Test // test du temps dans le constructeur simple
    void testSETemps0(){
        SasEntree entree = new SasEntree();
        assertEquals(0, entree.getTemps());
    }
    @Test // test de l'ecart temps dans le constructeur simple
    void testSEEcartTemps0(){
        SasEntree entree = new SasEntree();
        assertEquals(0, entree.getEcartTemps());
    }

    @Test
    void testToC(){
        SasEntree entree = new SasEntree();
        Activite activite = new Activite("activite");
        entree.ajouterSuccesseur(activite);
        StringBuilder test = new StringBuilder();
        test.append("    entrer(SasEntree);\n" +
                "    delai(6,3);\n" +
                "    transfert(SasEntree, activite);\n");
        assertEquals(test.toString(), entree.toC());
    }
}
