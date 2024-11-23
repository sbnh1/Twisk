package twiskIG;

import org.junit.jupiter.api.Test;
import twisk.outils.FabriqueIdentifiant;

import static org.junit.jupiter.api.Assertions.*;

public class testFabriqueIdentifiant {

    @Test
    public void testGetInstance() {
        FabriqueIdentifiant instance1 = FabriqueIdentifiant.getInstance();
        FabriqueIdentifiant instance2 = FabriqueIdentifiant.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testGetID(){
        FabriqueIdentifiant instance = FabriqueIdentifiant.getInstance();
        String identifiant = instance.getIdentifiantEtape();
        String idTest = "Activite1";
        assertEquals(identifiant, idTest);
    }

    @Test
    public void testGetId2(){
        FabriqueIdentifiant instance = FabriqueIdentifiant.getInstance();
        String identifiant1 = instance.getIdentifiantEtape();
        String identifiant2 = instance.getIdentifiantEtape();
        String identifiant3 = instance.getIdentifiantEtape();
        String identifiant4 = instance.getIdentifiantEtape();
        String identifiant5 = instance.getIdentifiantEtape();
        String idTest = "Activite5";
        assertEquals(identifiant5, idTest);
    }
    @Test
    public void testReset(){
        FabriqueIdentifiant instance = FabriqueIdentifiant.getInstance();
        String identifiant1 = instance.getIdentifiantEtape();
        String identifiant2 = instance.getIdentifiantEtape();
        String identifiant3 = instance.getIdentifiantEtape();
        instance.reset();
        String id0 =  instance.getIdentifiantEtape();
        String idTest = "Activite1";
        assertEquals(id0, idTest);
    }
}
