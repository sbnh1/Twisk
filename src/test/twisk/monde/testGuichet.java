package twisk.monde;

import twisk.monde.Activite;
import twisk.monde.ActiviteRestreinte;
import twisk.monde.Guichet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class testGuichet {
    @Test
    public void testNbJeton0(){ //test quand nbjetons = 0
        Guichet guichet = new Guichet("Guichet1", 0);
        assertEquals(0, guichet.getNbJetons());
    }

    @Test
    public void testNbJetons(){ //test quand nbjetons > 0
        Guichet guichet = new Guichet("Guichet2", 46);
        assertEquals(46, guichet.getNbJetons());
    }
    //Test si les deux guichets ont les bons booléens
    @Test
    public void testEstUnGuichetAvecJetons(){
        Guichet guichet = new Guichet("Guichet1", 12);
        assertTrue(guichet.getNbJetons() > 0);
        assertTrue(guichet.estUnGuichet());
    }
    @Test
    public void testEstUnGuichetSansJetons(){
        Guichet guichet = new Guichet("Guichet1");
        assertEquals(0,guichet.getNbJetons());
        assertTrue(guichet.estUnGuichet());
    }
    //Test si les deux guichets ne sont pas des activités
    @Test
    public void testNEstPasUneActiviteAvecJetons(){ //test guichet avec jetons
        Guichet guichet = new Guichet("Guichet1", 13);
        assertFalse(guichet.estUneActivite());
    }

    @Test
    public void testNEstPasUneActiviteSansJetons(){ //test guichet sans jetons
        Guichet guichet = new Guichet("Guichet2");
        assertFalse(guichet.estUneActivite());
    }

    @Test
    public void testToC() {
        ActiviteRestreinte activiteRestreinte = new ActiviteRestreinte("Successeur");
        Guichet guichet = new Guichet("Guichet", 5); // Exemple : 5 jetons
        Activite activite = new Activite("activite");
        guichet.ajouterSuccesseur(activiteRestreinte);
        activiteRestreinte.ajouterSuccesseur(activite);

        StringBuilder expected = new StringBuilder();
        expected.append("    delai(4,1);\n");
        expected.append("    P(ids, Guichet_semaphore);\n");
        expected.append("    transfert(Guichet, Successeur);\n");
        expected.append("    delai(6,2);\n");
        expected.append("    transfert(Successeur, activite);\n");
        expected.append("    V(ids, Guichet_semaphore);\n");

        assertEquals(expected.toString(), guichet.toC());
    }
}
