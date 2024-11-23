package twiskIG;

import org.junit.jupiter.api.Test;
import twisk.mondeIG.ActiviteIG;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testActiviteIG {
    @Test
    public void testNom(){
        ActiviteIG activite =  new ActiviteIG("activite", 90,90);
        assertEquals(activite.getNom(), "activite");
    }
     @Test
    public void testIdentifiant(){
        FabriqueIdentifiant instance = FabriqueIdentifiant.getInstance();
        instance.reset();
        ActiviteIG activite = new ActiviteIG("activite", 90,90);
        assertEquals(activite.getIdentifiant(),"Activité1");
    }
    @Test
    public void testLargeur(){
        ActiviteIG activite = new ActiviteIG("activite", 90,90);
        assertEquals(activite.getLargeur(),90);
    }
    @Test
    public void testHauteur(){
        ActiviteIG activite = new ActiviteIG("activite", 90,90);
        assertEquals(activite.getHauteur(),90);
    }
    @Test
    public void testPosX(){
        ActiviteIG activite = new ActiviteIG("activite", 90,90);
        assertTrue(activite.getPosX() >= 200 && activite.getPosX() < 500);
    }
    @Test
    public void testPosY(){
        ActiviteIG activite = new ActiviteIG("activite", 90,90);
        assertTrue(activite.getPosY() >= 200 && activite.getPosY() < 500);

    }
}
