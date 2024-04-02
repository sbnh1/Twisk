package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.SasEntree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class testSasEntree {
    @Test // test du temps dans le constructeur simple
    public void testSETemps0(){
        SasEntree entree = new SasEntree();
        assertEquals(0, entree.getTemps());
    }
    @Test // test de l'ecart temps dans le constructeur simple
    public void testSEEcartTemps0(){
        SasEntree entree = new SasEntree();
        assertEquals(0, entree.getEcartTemps());
    }

    @Test
    public void testToC(){
        SasEntree entree = new SasEntree();
        Activite activite = new Activite("activite");
        entree.ajouterSuccesseur(activite);
        StringBuilder test = new StringBuilder();
        test.append("    delai(6,3);\n" +
                "    transfert(SasEntree, activite);\n");
        assertEquals(test.toString(), entree.toC());
    }
}
