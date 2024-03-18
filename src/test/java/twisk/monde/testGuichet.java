package test.java.twisk.monde;

import main.java.twisk.monde.ActiviteRestreinte;
import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class testGuichet {
    @Test
    void testNbJeton0(){ //test quand nbjetons = 0
        Guichet guichet = new Guichet("Guichet1", 0);
        assertEquals(0, guichet.getNbJetons());
    }

    @Test
    void testNbJetons(){ //test quand nbjetons > 0
        Guichet guichet = new Guichet("Guichet2", 46);
        assertEquals(46, guichet.getNbJetons());
    }
    //Test si les deux guichets ont les bons booléens
    @Test
    void testEstUnGuichetAvecJetons(){
        Guichet guichet = new Guichet("Guichet1", 12);
        assertTrue(guichet.getNbJetons() > 0);
        assertTrue(guichet.estUnGuichet());
    }
    @Test
    void testEstUnGuichetSansJetons(){
        Guichet guichet = new Guichet("Guichet1");
        assertEquals(0,guichet.getNbJetons());
        assertTrue(guichet.estUnGuichet());
    }
    //Test si les deux guichets ne sont pas des activités
    @Test
    void testNEstPasUneActiviteAvecJetons(){ //test guichet avec jetons
        Guichet guichet = new Guichet("Guichet1", 13);
        assertFalse(guichet.estUneActivite());
    }

    @Test
    void testNEstPasUneActiviteSansJetons(){ //test guichet sans jetons
        Guichet guichet = new Guichet("Guichet2");
        assertFalse(guichet.estUneActivite());
    }

    @Test
    void testToC() {
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Successeur");
        Guichet guichet = new Guichet("Guichet", 5); // Exemple : 5 jetons
        guichet.ajouterSuccesseur(activiteRestreinte);

        StringBuilder expected = new StringBuilder();
        expected.append("    delai(4,1);\n");
        expected.append("    P(ids, guichet_semaphore" + guichet.getNumeroSemaphore() + ");\n");
        expected.append("    transfert(" + guichet.getNom() + ", " + activiteRestreinte.getNom() + ");\n");
        expected.append("    delai(6,2);\n");
        expected.append("    transfert(" + activiteRestreinte.getNom() + "," + activiteRestreinte.getSuccesseur().getEtape(0) +");\n");
        expected.append("    V(ids, guichet_semaphore" + guichet.getNumeroSemaphore() + ");\n");

        assertEquals(expected.toString(), guichet.toC().toString());
    }
}
