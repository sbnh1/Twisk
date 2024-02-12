package test.twisk.monde;

import twisk.monde.Guichet;
import twisk.monde.Activite;
import twisk.monde.Monde;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testMonde {
    @Test
    void testNbEtapes(){
        Monde monde = new Monde();
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet1 = new Guichet("Guichet1");
        Guichet guichet2 = new Guichet("Guichet2");
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
        Activite etape1 = new Activite("Activite1");
        Activite etape2 = new Activite("Activite2");
        monde.ajouter(etape1, etape2);
        assertEquals(0, monde.nbGuichets());
    }
    @Test
    void testNbGuichet(){
        Monde monde = new Monde();
        Activite etape1 = new Activite("Activite1");
        Guichet guichet1 = new Guichet("Guichet1");
        Activite etape2 = new Activite("Activite2");
        Guichet guichet2 = new Guichet("Guichet2");
        monde.ajouter(etape1, etape2, guichet1, guichet2);
        assertEquals(2, monde.nbGuichets());
    }

}
