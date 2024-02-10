package test.java;

import main.java.Etape;
import main.java.GestionnaireEtapes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testGestionnaireEtapes {

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
    void testNbEtapes(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Etape etape1 = new EtapeActivite("Activite1");
        Etape etape2 = new EtapeActivite("Activite2");
        Etape guichet1 = new EtapeGuichet("Guichet1");
        gestionnaireEtapes.ajouter(etape1, etape2, guichet1);
        assertEquals(3, gestionnaireEtapes.nbEtapes());
    }
    @Test
    void testNbEtapes0(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        assertEquals(0, gestionnaireEtapes.nbEtapes());
    }
    @Test
    void testNbGuichet(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Etape etape1 = new EtapeActivite("Activite1");
        Etape etape2 = new EtapeActivite("Activite2");
        Etape guichet1 = new EtapeGuichet("Guichet1");
        gestionnaireEtapes.ajouter(etape1, etape2, guichet1);
        assertEquals(1, gestionnaireEtapes.nbGuichets());
    }
    @Test
    void testNbGuichet0(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Etape etape1 = new EtapeActivite("Activite1");
        Etape etape2 = new EtapeActivite("Activite2");
        gestionnaireEtapes.ajouter(etape1, etape2);
        assertEquals(0, gestionnaireEtapes.nbGuichets());
    }

}
