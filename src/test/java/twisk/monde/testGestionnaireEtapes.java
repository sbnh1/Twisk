package test.java.twisk.monde;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.GestionnaireEtapes;
import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testGestionnaireEtapes {
    @Test
    public void testNbEtapes(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet1 = new Guichet("Guichet1");
        gestionnaireEtapes.ajouter(etape1, etape2, guichet1);
        assertEquals(3, gestionnaireEtapes.nbEtapes());
    }
    @Test
    public void testNbEtapes0(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        assertEquals(0, gestionnaireEtapes.nbEtapes());
    }
    @Test
    public void testNbGuichet(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet1 = new Guichet("Guichet1");
        gestionnaireEtapes.ajouter(etape1, etape2, guichet1);
        assertEquals(1, gestionnaireEtapes.nbGuichets());
    }
    @Test
    public void testNbGuichet0(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        gestionnaireEtapes.ajouter(etape1, etape2);
        assertEquals(0, gestionnaireEtapes.nbGuichets());
    }
    @Test
    public void testgetEtape1(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        gestionnaireEtapes.ajouter(etape1, etape2);
        assertEquals(etape1, gestionnaireEtapes.getEtape(0));
    }
    @Test
    public void testgetEtape2(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        gestionnaireEtapes.ajouter(etape1, etape2);
        assertEquals(etape2, gestionnaireEtapes.getEtape(1));
    }
    @Test
    public void testgetEtape3(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        gestionnaireEtapes.ajouter(etape1, etape2);
        assertNull(gestionnaireEtapes.getEtape(2));
    }
}
