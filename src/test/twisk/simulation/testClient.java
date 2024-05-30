package twisk.simulation;

import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.simulation.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
