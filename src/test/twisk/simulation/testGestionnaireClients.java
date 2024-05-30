package twisk.simulation;

import twisk.monde.Activite;
import twisk.monde.Etape;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testGestionnaireClients {
    @Test
    public void testConstructeur(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        assertNotNull(gestionnaireClients);
    }

    @Test
    public void testSetClients(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        gestionnaireClients.setClients(12, 34, 58, 80);
        assertNotEquals(0, gestionnaireClients.listClient.size());
    }

    @Test
    public void testGetClients(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        gestionnaireClients.setClients(12, 34, 58, 80);
        assertNotNull(gestionnaireClients.getClients(12));
        assertNotNull(gestionnaireClients.getClients(34));
        assertNotNull(gestionnaireClients.getClients(58));
        assertNotNull(gestionnaireClients.getClients(80));
        assertNull(gestionnaireClients.getClients(100)); // Client non existant
    }

    @Test
    public void testAllerA(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        gestionnaireClients.setClients(12, 34, 58, 80);
        Etape etape = new Activite("Test");
        gestionnaireClients.allerA(12, etape, 1);
        assertEquals(etape, gestionnaireClients.getClients(12).getEtape());
        assertEquals(1, gestionnaireClients.getClients(12).getRang());
    }

    @Test
    public void testSize(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        assertEquals(0, gestionnaireClients.size());
        gestionnaireClients.setClients(12, 34, 58, 80);
        assertEquals(4, gestionnaireClients.size());
    }
}
