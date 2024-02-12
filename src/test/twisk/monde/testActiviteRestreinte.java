package test.twisk.monde;

import twisk.monde.ActiviteRestreinte;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testActiviteRestreinte {
    @Test
    void testActiviteRTemps0(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite1");
        assertEquals(0, activiteRestreinte.getTemps());
    }
    @Test
    void testActiviteREcartTemps0(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2");
        assertEquals(0, activiteRestreinte.getEcartTemps());
    }
    //Test deuxième constructeur
    @Test
    void testActiviteRTemps(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite1",12, 3);
        assertNotEquals(0, activiteRestreinte.getTemps());
    }
    @Test
    void testActiviteEcartTemps(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2",12, 3);
        assertNotEquals(0, activiteRestreinte.getEcartTemps());
    }
    //Test des booléens sur les deux constructeurs
    @Test
    void testEstUneActiviteR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2",12, 3);
        assertTrue(activiteRestreinte.estUneActivite());
    }
    @Test
    void testNestPasUneGuichetR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2",12, 3);
        assertFalse(activiteRestreinte.estUnGuichet());
    }
    @Test
    void testEstUneActiviteSimpleR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2");
        assertTrue(activiteRestreinte.estUneActivite());
    }
    @Test
    void testNestPasUneGuichetSimpleR(){
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Activite2");
        assertFalse(activiteRestreinte.estUnGuichet());
    }
}
