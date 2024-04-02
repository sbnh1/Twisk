package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.ActiviteRestreinte;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testActiviteRestreinte {
    @Test
    public void testActiviteRTemps0(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite1");
        assertEquals(0, activiteRestreinte.getTemps());
    }
    @Test
    public void testActiviteREcartTemps0(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2");
        assertEquals(0, activiteRestreinte.getEcartTemps());
    }
    //Test deuxième constructeur
    @Test
    public void testActiviteRTemps(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite1",12, 3);
        assertNotEquals(0, activiteRestreinte.getTemps());
    }
    @Test
    public void testActiviteEcartTemps(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2",12, 3);
        assertNotEquals(0, activiteRestreinte.getEcartTemps());
    }
    //Test des booléens sur les deux constructeurs
    @Test
    public void testEstUneActiviteR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2",12, 3);
        assertTrue(activiteRestreinte.estUneActivite());
    }
    @Test
    public void testNestPasUneGuichetR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2",12, 3);
        assertFalse(activiteRestreinte.estUnGuichet());
    }
    @Test
    public void testEstUneActiviteSimpleR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2");
        assertTrue(activiteRestreinte.estUneActivite());
    }
    @Test
    public void testNestPasUneGuichetSimpleR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2");
        assertFalse(activiteRestreinte.estUnGuichet());
    }

    @Test
    public void testToCAvecSuccesseur() {
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("ActiviteTest");
        Activite activite = new Activite("activite");
        activiteRestreinte.ajouterSuccesseur(activite);
        StringBuilder test = new StringBuilder();
        test.append("    delai(6,2);\n");
        test.append("    transfert(ActiviteTest, activite);\n");
        assertEquals(test.toString(), activiteRestreinte.toC());
    }
    @Test
    public void testToCSansSuccesseur(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("activitéRestreinte");
        String string = new String("");
        assertEquals(string, activiteRestreinte.toC());
    }

}
