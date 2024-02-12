package test.java;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.GestionnaireEtapes;
import main.java.twisk.monde.Guichet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testGestionnaireEtapes {
    @Test
    void testNbEtapes(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet1 = new Guichet("Guichet1");
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
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet1 = new Guichet("Guichet1");
        gestionnaireEtapes.ajouter(etape1, etape2, guichet1);
        assertEquals(1, gestionnaireEtapes.nbGuichets());
    }
    @Test
    void testNbGuichet0(){
        GestionnaireEtapes gestionnaireEtapes = new GestionnaireEtapes();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        gestionnaireEtapes.ajouter(etape1, etape2);
        assertEquals(0, gestionnaireEtapes.nbGuichets());
    }

}
