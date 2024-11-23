package twiskIG;

import org.junit.jupiter.api.Test;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.PointDeControleIG;

import static org.junit.jupiter.api.Assertions.*;

public class testPointDeControleIG {
    private EtapeIG etape;

    @Test
    public void testPositionX(){
        System.out.println("adtgqsdg");
        PointDeControleIG test = new PointDeControleIG(30,40,"id", etape);
        System.out.println("atet");
        assertEquals(test.getPositionX(), 30);
    }
    @Test
    public void testPositionY(){
        PointDeControleIG test = new PointDeControleIG(30,40,"id", etape);
        assertEquals(test.getPositionY(), 40);
    }
    @Test
    public void testIdentifiant(){
        PointDeControleIG test = new PointDeControleIG(30,40,"id", etape);
        assertEquals(test.getIdentifiant(), "id");
    }
    @Test
    public void testEtape(){
        PointDeControleIG test = new PointDeControleIG(30,40,"id", etape);
        assertEquals(test.getEtapeIG(), etape);
    }

    @Test
    public void testId(){
        PointDeControleIG test = new PointDeControleIG(30,40,"id", etape);
        System.out.println("id : " + test.getEtapeIG());
    }
}
