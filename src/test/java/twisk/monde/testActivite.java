package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class testActivite {

    //test du constructeur simple
    @Test
    public void testActiviteTemps0(){
        Activite activite = new Activite("Activite1");
        assertEquals(0, activite.getTemps());
    }
    @Test
    public void testActiviteEcartTemps0(){
        Activite activite = new Activite("Activite2");
        assertEquals(0, activite.getEcartTemps());
    }

    //Test deuxième constructeur
    @Test
    public void testActiviteTemps(){
        Activite activite = new Activite("Activite1",12, 3);
        assertNotEquals(0, activite.getTemps());
    }
    @Test
    public void testActiviteEcartTemps(){
        Activite activite = new Activite("Activite2",12, 3);
        assertNotEquals(0, activite.getEcartTemps());
    }
    //Test des booléens sur les deux constructeurs
    @Test
    public void testEstUneActivite(){
        Activite activite = new Activite("Activite2",12, 3);
        assertTrue(activite.estUneActivite());
    }
    @Test
    public void testNestPasUneGuichet(){
        Activite activite = new Activite("Activite2",12, 3);
        assertFalse(activite.estUnGuichet());
    }

    @Test
    public void testEstUneActiviteSimple(){
        Activite activite = new Activite("Activite2");
        assertTrue(activite.estUneActivite());
    }
    @Test
    public void testNestPasUneGuichetSimple(){
        Activite activite = new Activite("Activite2");
        assertFalse(activite.estUnGuichet());
    }

    @Test
    public void testToCAvecSuccesseur(){
        Activite activite = new Activite("Activite");
        Guichet guichet = new Guichet("guichet1");
        activite.ajouterSuccesseur(guichet);
        StringBuilder test = new StringBuilder();
        test.append("    delai(6,3);\n" +
                "    transfert(Activite,guichet1);\n");
        assertEquals(test.toString(), activite.toC().toString());
    }
    @Test
    public void testToCSansSuccesseur(){
        Activite activite = new Activite("activite");
        String string = new String("");
        assertEquals(string, activite.toC());
    }
}
