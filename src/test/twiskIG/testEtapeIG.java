package twiskIG;

import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.*;

public class testEtapeIG {
    @Test
    public void testCreationEtapeIG() {
        EtapeIG etape = new ActiviteIG("activite", 90, 90);
        assertNotNull(etape);
    }
    @Test
    public void testNom(){
        EtapeIG etape = new ActiviteIG("activité", 90, 90);
        assertEquals("activité", etape.getNom());
    }
    @Test
    public void testIdentifiant(){
        FabriqueIdentifiant instance = FabriqueIdentifiant.getInstance();
        instance.reset();
        EtapeIG etape = new ActiviteIG("activité", 90, 90);
        String idTest = "Activité1";
        assertEquals(etape.getIdentifiant(), idTest);
    }
    @Test
    public void testLargeur(){
        EtapeIG etape = new ActiviteIG("activité", 90, 90);
        assertTrue(etape.getLargeur() == 90);
    }
    @Test
    public void testHauteur(){
        EtapeIG etape = new ActiviteIG("activité", 90, 90);
        assertTrue(etape.getHauteur() == 90);
    }
    @Test
    public void testPosX(){
        EtapeIG etape = new ActiviteIG("activité", 90, 90);
        assertTrue(etape.getPosX() >= 200 && etape.getPosX() < 500);

    }
    @Test
    public void testPosY(){
        EtapeIG etape = new ActiviteIG("activité", 90, 90);
        assertTrue(etape.getPosY() >= 200 && etape.getPosY() < 500);

    }
}
