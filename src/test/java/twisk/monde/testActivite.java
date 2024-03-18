package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class testActivite {

    //test du constructeur simple
    @Test
    void testActiviteTemps0(){
        Activite activite = new Activite("Activite1");
        assertEquals(0, activite.getTemps());
    }
    @Test
    void testActiviteEcartTemps0(){
        Activite activite = new Activite("Activite2");
        assertEquals(0, activite.getEcartTemps());
    }

    //Test deuxième constructeur
    @Test
    void testActiviteTemps(){
        Activite activite = new Activite("Activite1",12, 3);
        assertNotEquals(0, activite.getTemps());
    }
    @Test
    void testActiviteEcartTemps(){
        Activite activite = new Activite("Activite2",12, 3);
        assertNotEquals(0, activite.getEcartTemps());
    }
    //Test des booléens sur les deux constructeurs
    @Test
    void testEstUneActivite(){
        Activite activite = new Activite("Activite2",12, 3);
        assertTrue(activite.estUneActivite());
    }
    @Test
    void testNestPasUneGuichet(){
        Activite activite = new Activite("Activite2",12, 3);
        assertFalse(activite.estUnGuichet());
    }

    @Test
    void testEstUneActiviteSimple(){
        Activite activite = new Activite("Activite2");
        assertTrue(activite.estUneActivite());
    }
    @Test
    void testNestPasUneGuichetSimple(){
        Activite activite = new Activite("Activite2");
        assertFalse(activite.estUnGuichet());
    }

    @Test
    void testToC(){
        Activite activite = new Activite("Activite");
        StringBuilder test = new StringBuilder();
        test.append("delai(" + activite.getTemps() + "," + activite.getEcartTemps() + ");\n" +
                "transfert(" + activite.getId() + "," + activite.getSuccesseur().getEtape(0) +");\n");
        assertEquals(test.toString(), activite.toC().toString());
    }
}
