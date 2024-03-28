package test.java.twisk.monde;

import main.java.twisk.monde.SasSortie;
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

    @Test
    void testToC(){
        SasSortie sortie = new SasSortie("sortie");
        StringBuilder test = new StringBuilder();
        assertEquals(test.toString(), sortie.toC().toString());
    }
}
