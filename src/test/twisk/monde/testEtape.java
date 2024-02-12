package test.twisk.monde;


import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.outils.FabriqueNumero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testEtape {
    @Test
    void testAjouterSuccesseur() {
        Activite etape1 = new Activite("Etape1");
        Guichet etape2 = new Guichet("Etape2");
        Guichet etape3 = new Guichet("Etape3");
        etape1.ajouterSuccesseur(etape2, etape3);
        assertEquals(2, etape1.nbSuccesseurs());
    }

    //Test des booléens avec des guichets
    @Test
    void testEstUneActiviteFaux() {
        Guichet etape = new Guichet("Guichet1");
        assertFalse(etape.estUneActivite());
    }
    @Test
    void testEstUnGuichetVrai() {
        Guichet etape = new Guichet("Guichet2");
        assertTrue(etape.estUnGuichet());
    }

    //Test des boolées avec des activitées
    @Test
    void testEstUneActiviteVrai(){
        Activite etape = new Activite("Activite1");
        assertTrue(etape.estUneActivite());
    }
    @Test
    void testEstUnGuichetFaux(){
        Activite etape = new Activite("Activite2");
        assertFalse(etape.estUnGuichet());
    }

    @Test //test de l'id sur une Etape activité = 0
    void testIDAct0(){
        FabriqueNumero.getInstance().reset();
        Activite etape = new Activite("Activité1");
        assertEquals(0, etape.getId());
    }
    @Test //test de l'id sur une Etape activité > 0
    void testIDAct(){
        Activite etape1 = new Activite("Activite1", 4, 5);
        Activite etape2 = new Activite("Activite2", 12, 3);
        Guichet etape3 = new Guichet("Guichet1");
        Activite etape4 = new Activite("Activité3", 12, 3);
        assertEquals(3, etape4.getId());
    }

    @Test //test de l'id sur une Etape Guichet = 0
    void testIDGui0(){
        FabriqueNumero.getInstance().reset();
        Guichet etape = new Guichet("Guichet1");
        assertEquals(0, etape.getId());
    }
    @Test //test de l'id sur une Etape guichet > 0
    void testIDGui(){
        FabriqueNumero.getInstance().reset();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet etape3 = new Guichet("Guichet1");
        Guichet etape4 = new Guichet("Guichet2");
        assertEquals(3, etape4.getId());
    }

    @Test //test de reset quand une seul etape est ajouté
    void testReset0(){
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet etape3 = new Guichet("Guichet1");
        Guichet etape4 = new Guichet("Guichet2");
        FabriqueNumero.getInstance().reset();
        Etape etape0 = new Guichet("Guichet0");
        assertEquals(0, etape0.getId());
    }

    @Test // test de reset avec plusieurs etapes
    void testReset(){
        Activite etape1 = new Activite("Activite1");
        FabriqueNumero.getInstance().reset();
        Activite etape2 = new Activite("Activite0");
        Guichet etape3 = new Guichet("Guichet1");
        Guichet etape4 = new Guichet("Guichet2");
        Guichet etape0 = new Guichet("Guichet3");
        assertEquals(3, etape0.getId());
    }

    @Test // test de reset quand on l'utilise plusieurs fois
    void testResetMultiple(){
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet etape3 = new Guichet("Guichet4");
        Guichet etape4 = new Guichet("Guichet5");
        FabriqueNumero.getInstance().reset();
        Activite etape5 = new Activite("Activite3");
        FabriqueNumero.getInstance().reset();
        Activite etape6 = new Activite("Activite0");
        Guichet etape7 = new Guichet("Guichet1");
        Guichet etape8 = new Guichet("Guichet2");
        Guichet etape0 = new Guichet("Guichet3");
        assertEquals(3, etape0.getId());
    }


    @Test //test du Semaphore sur une Etape Guichet = 0
    void testSemaphores0(){
        FabriqueNumero.getInstance().resetNumeroSemaphore();
        Guichet guichet = new Guichet("Guichet1");
        assertEquals(1, guichet.getNumeroSemaphore());
    }
    @Test //test du Semaphore sur une Etape guichet > 0
    void testSemaphores(){
        FabriqueNumero.getInstance().resetNumeroSemaphore();
        Guichet guichet1 = new Guichet("Guichet1");
        Guichet guichet2 = new Guichet("Guichet2");
        assertEquals(2, guichet2.getNumeroSemaphore());
    }

}

