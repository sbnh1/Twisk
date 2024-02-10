package test.java;

import main.java.Etape;
import main.java.Monde;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testMonde {

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
        Monde monde = new Monde();
        Etape etape1 = new EtapeActivite("Activite1");
        Etape etape2 = new EtapeActivite("Activite2");
        Etape guichet1 = new EtapeGuichet("Guichet1");
        Etape guichet2 = new EtapeGuichet("Guichet2");
        monde.ajouter(etape1, etape2, guichet1, guichet2);
        assertEquals(4, monde.nbEtapes());
    }
    @Test
    void testNbEtapes0(){
        Monde monde = new Monde();
        assertEquals(0, monde.nbEtapes());
    }
    @Test
    void testNbGuichet0(){
        Monde monde = new Monde();
        Etape etape1 = new EtapeActivite("Activite1");
        Etape etape2 = new EtapeActivite("Activite2");
        monde.ajouter(etape1, etape2);
        assertEquals(0, monde.nbGuichets());
    }
    @Test
    void testNbGuichet(){
        Monde monde = new Monde();
        Etape etape1 = new EtapeActivite("Activite1");
        Etape guichet1 = new EtapeGuichet("Guichet1");
        Etape etape2 = new EtapeActivite("Activite2");
        Etape guichet2 = new EtapeGuichet("Guichet2");
        monde.ajouter(etape1, etape2, guichet1, guichet2);
        assertEquals(2, monde.nbGuichets());
    }

}
