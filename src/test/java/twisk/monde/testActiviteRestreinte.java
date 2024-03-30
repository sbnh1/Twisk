package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.ActiviteRestreinte;
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

    @Test
    void testToC() {
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("ActiviteTest");
        Activite activite = new Activite("activite");
        activiteRestreinte.ajouterSuccesseur(activite);
        StringBuilder test = new StringBuilder();
        test.append("    delai(6,2);\n");
        test.append("    transfert(ActiviteTest, activite);\n");
        assertEquals(test.toString(), activiteRestreinte.toC());
    }


}
