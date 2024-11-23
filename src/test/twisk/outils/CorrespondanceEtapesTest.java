package twisk.outils;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;


public class CorrespondanceEtapesTest {
    @Test
    public void testAjoutEtRecuperation() {
        CorrespondanceEtapes correspondanceEtapes = new CorrespondanceEtapes();
        Etape etape = new Activite("Etape Test");
        EtapeIG etapeIG = new ActiviteIG("etapeIG", 30,40);
        correspondanceEtapes.ajouter(etapeIG, etape);
        Etape etapeRecuperee = correspondanceEtapes.get(etapeIG);
        assertNotNull(etapeRecuperee);
        assertEquals(etape, etapeRecuperee);
    }

    @Test
    public void testRecuperationInexistante() {
        CorrespondanceEtapes correspondanceEtapes = new CorrespondanceEtapes();
        EtapeIG etapeIGInexistante = new ActiviteIG("EtapeIG Inexistante", 30,40);
        assertNull(correspondanceEtapes.get(etapeIGInexistante));
    }

    @Test
    public void testAjoutRemplacement() {
        CorrespondanceEtapes correspondanceEtapes = new CorrespondanceEtapes();
        Etape etape1 = new Activite("Etape 1");
        EtapeIG etapeIG = new ActiviteIG("EtapeIG Test",30,40);
        correspondanceEtapes.ajouter(etapeIG, etape1);
        Etape etape2 = new Activite("Etape 2");
        correspondanceEtapes.ajouter(etapeIG, etape2);
        assertEquals(etape2, correspondanceEtapes.get(etapeIG));
    }
}
