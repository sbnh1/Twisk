package test.twisk.monde;

import twisk.monde.SasEntree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
}
