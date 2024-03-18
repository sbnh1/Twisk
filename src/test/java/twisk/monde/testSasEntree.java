package test.java.twisk.monde;

import main.java.twisk.monde.SasEntree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class testSasEntree {
    @Test // test du temps dans le constructeur simple
    void testSETemps0(){
        SasEntree entree = new SasEntree("Entrée1");
        assertEquals(0, entree.getTemps());
    }
    @Test // test de l'ecart temps dans le constructeur simple
    void testSEEcartTemps0(){
        SasEntree entree = new SasEntree("Entrée1");
        assertEquals(0, entree.getEcartTemps());
    }
    @Test // test du temps > 0
    void testSETemps(){
        SasEntree entree = new SasEntree("Entrée1", 12, 2);
        assertEquals(12, entree.getTemps());
    }
    @Test // test de l'ecart temps > 0
    void testSEEcartTemps(){
        SasEntree entree = new SasEntree("Entrée1", 12, 2);
        assertEquals(2, entree.getEcartTemps());
    }

    @Test
    void testToC(){
        SasEntree entree = new SasEntree("entree");
        StringBuilder test = new StringBuilder();
        test.append("entrer(sasEntree);\n" +
                "    delai(6,3);\n" +
                "    transfert(sasEntree, " + entree.getSuccesseur().getEtape(0) +");");
        assertEquals(test.toString(), entree.toC().toString());
    }
}
