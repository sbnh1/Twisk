package test.java.twisk.simulation;

import main.java.twisk.monde.Activite;
import main.java.twisk.monde.Etape;
import main.java.twisk.simulation.Client;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class testClient {
    @Test
    public void testConstructeur(){
        Client client = new Client(12);
        assertNotNull(client);
    }
    @Test
    public void testGetNumeroClient(){
        Client client = new Client(17);
        assertEquals(17, client.getNumeroClient());
    }
    @Test
    public void testGetNumeroClient2(){
        Client client = new Client(17);
        client.allerA(null, 1);
        assertEquals(17, client.getNumeroClient());
    }
    @Test
    public void testGetRang(){
        Client client = new Client(70);
        client.allerA(null, 3);
        assertEquals(3, client.getRang());
    }
    @Test
    public void testGetEtape(){
        Client client = new Client(13);
        Etape etape = new Activite("activité");
        client.allerA(etape, 13);
        assertEquals(etape, client.getEtape());
    }
}
