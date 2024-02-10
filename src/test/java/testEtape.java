package test.java;


import main.java.Etape;
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
        public Boolean estUneActivite() { return false; }
        @Override
        public Boolean estUnGuichet() { return true; }
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
}

