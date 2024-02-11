package test.java;


import main.java.Activite;
import main.java.Etape;
import main.java.Guichet;
import main.java.outils.FabriqueNumero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testEtape {

    private static class EtapeGuichet extends Etape {
        public EtapeGuichet(String nom) { super(nom); }
        @Override
        public Boolean estUneActivite() { return false; }
        @Override
        public Boolean estUnGuichet() { return true; }
    }

    private static class EtapeActivite extends Etape {
        public EtapeActivite(String nom) { super(nom); }
        @Override
        public Boolean estUneActivite() { return true; }
        @Override
        public Boolean estUnGuichet() { return false; }
    }

    @Test
    void testAjouterSuccesseur() {
        Etape etape1 = new EtapeGuichet("Etape1");
        Etape etape2 = new EtapeGuichet("Etape2");
        Etape etape3 = new EtapeGuichet("Etape3");
        etape1.ajouterSuccesseur(etape2, etape3);
        assertEquals(2, etape1.nbSuccesseurs());
    }

    //Test des booléens avec des guichets
    @Test
    void testEstUneActiviteFaux() {
        Etape etape = new EtapeGuichet("Guichet1");
        assertFalse(etape.estUneActivite());
    }
    @Test
    void testEstUnGuichetVrai() {
        Etape etape = new EtapeGuichet("Guichet2");
        assertTrue(etape.estUnGuichet());
    }

    //Test des boolées avec des activitées
    @Test
    void testEstUneActiviteVrai(){
        Etape etape = new EtapeActivite("Activite1");
        assertTrue(etape.estUneActivite());
    }
    @Test
    void testEstUnGuichetFaux(){
        Etape etape = new EtapeActivite("Activite2");
        assertFalse(etape.estUnGuichet());
    }

    @Test //test de l'id sur une Etape activité = 0
    void testIDAct0(){
        Etape etape = new Activite("Activité1");
        assertEquals(0, etape.getId());
    }
    @Test //test de l'id sur une Etape activité > 0
    void testIDAct(){
        Etape etape1 = new Activite("Activite1", 4, 5);
        Etape etape2 = new Activite("Activite2", 12, 3);
        Etape etape3 = new Guichet("Guichet1");
        Etape etape4 = new Activite("Activité3", 12, 3);
        assertEquals(3, etape4.getId());
    }

    @Test //test de l'id sur une Etape Guichet = 0
    void testIDGui0(){
        Etape etape = new Guichet("Guichet1");
        assertEquals(0, etape.getId());
    }
    @Test //test de l'id sur une Etape guichet > 0
    void testIDGui(){
        Etape etape1 = new Activite("Activite1");
        Etape etape2 = new Activite("Activite2");
        Etape etape3 = new Guichet("Guichet1");
        Etape etape4 = new Guichet("Guichet2");
        assertEquals(3, etape4.getId());
    }

    @Test //test de reset quand une seul etape est ajouté
    void testReset0(){
        Etape etape1 = new Activite("Activite1");
        Etape etape2 = new Activite("Activite2");
        Etape etape3 = new Guichet("Guichet1");
        Etape etape4 = new Guichet("Guichet2");
        FabriqueNumero.getInstance().reset();
        Etape etape0 = new Guichet("Guichet0");
        assertEquals(0, etape0.getId());
    }

    @Test // test de reset avec plusieurs etapes
    void testReset(){
        Etape etape1 = new Activite("Activite1");
        FabriqueNumero.getInstance().reset();
        Etape etape2 = new Activite("Activite0");
        Etape etape3 = new Guichet("Guichet1");
        Etape etape4 = new Guichet("Guichet2");
        Etape etape0 = new Guichet("Guichet3");
        assertEquals(3, etape0.getId());
    }

    @Test // test de reset quand on l'utilise plusieurs fois
    void testResetMultiple(){
        Etape etape1 = new Activite("Activite1");
        Etape etape2 = new Activite("Activite2");
        Etape etape3 = new Guichet("Guichet4");
        Etape etape4 = new Guichet("Guichet5");
        FabriqueNumero.getInstance().reset();
        Etape etape5 = new Activite("Activite3");
        FabriqueNumero.getInstance().reset();
        Etape etape6 = new Activite("Activite0");
        Etape etape7 = new Guichet("Guichet1");
        Etape etape8 = new Guichet("Guichet2");
        Etape etape0 = new Guichet("Guichet3");
        assertEquals(3, etape0.getId());
    }


    @Test //test du Semaphore sur une Etape Guichet = 0
    void testSemaphores0(){
        Guichet guichet = new Guichet("Guichet1");
        assertEquals(1, guichet.getNumeroSemaphore());
    }
    @Test //test du Semaphore sur une Etape guichet > 0
    void testSemaphores(){
        Guichet guichet1 = new Guichet("Guichet1");
        Guichet guichet2 = new Guichet("Guichet2");
        assertEquals(2, guichet2.getNumeroSemaphore());
    }

}

