package test.java.twisk.monde;

import main.java.twisk.monde.SasSortie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class testSasSortie {
    @Test // test du temps dans le constructeur simple
    public void testSETemps0(){
        SasSortie sortie = new SasSortie();
        assertEquals(0, sortie.getTemps());
    }
    @Test // test de l'ecart temps dans le constructeur simple
    public void testSEEcartTemps0(){
        SasSortie sortie = new SasSortie();
        assertEquals(0, sortie.getEcartTemps());
    }

    @Test
    public void testToC(){
        SasSortie sortie = new SasSortie();
        StringBuilder test = new StringBuilder();
        assertEquals(test.toString(), sortie.toC());
    }
}
