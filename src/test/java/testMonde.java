package test.java;

import main.java.Etape;
import main.java.Monde;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    void test(){
        Monde monde = new Monde();
        Etape etape1 = new EtapeActivite("Activite");
        Etape etape2 = new EtapeActivite("Activite2");
        monde.ajouter(etape1, etape2);
        assertEquals(2, monde.nbEtapes());
    }
}
