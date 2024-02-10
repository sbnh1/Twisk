package test.java;

import main.java.Guichet;
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
}
