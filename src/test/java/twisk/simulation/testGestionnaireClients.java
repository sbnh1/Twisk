package test.java.twisk.simulation;

import main.java.twisk.simulation.GestionnaireClients;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class testGestionnaireClients {
    @Test
    public void testConstructeur(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        assertNotNull(gestionnaireClients);
    }
    @Test
    public void testSetClient(){
        GestionnaireClients gestionnaireClients = new GestionnaireClients();
        gestionnaireClients.setClients(12,34,58,80);
        assertNotEquals(0, gestionnaireClients.listClient.size());
    }
}
