package test.twisk.monde;

import twisk.monde.SasSortie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class testSasSortie {
    @Test // test du temps dans le constructeur simple
    void testSETemps0(){
        SasSortie sortie = new SasSortie("Sortie1");
        assertEquals(0, sortie.getTemps());
    }
    @Test // test de l'ecart temps dans le constructeur simple
    void testSEEcartTemps0(){
        SasSortie sortie = new SasSortie("Sortie1");
        assertEquals(0, sortie.getEcartTemps());
    }
    @Test // test du temps > 0
    void testSETemps(){
        SasSortie sortie = new SasSortie("Sortie1", 12, 2);
        assertEquals(12, sortie.getTemps());
    }
    @Test // test de l'ecart temps > 0
    void testSEEcartTemps(){
        SasSortie sortie = new SasSortie("Sortie1", 12, 2);
        assertEquals(2, sortie.getEcartTemps());
    }
}
