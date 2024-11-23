package twiskIG;

import org.junit.jupiter.api.Test;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.PointDeControleIG;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testArcIG {
    private EtapeIG etape;
    @Test
    public  void testGetPCDepart(){
        PointDeControleIG point1 = new PointDeControleIG(10.,20.,"id",etape);
        PointDeControleIG point2 = new PointDeControleIG(50.,60.,"id",etape);
        ArcIG arc = new ArcIG(point1, point2);
        assertEquals(arc.getPointDeControleDepart(), point1);
    }
    @Test
    public void testGetPCArrivee(){
        PointDeControleIG point1 = new PointDeControleIG(10.,20.,"id",etape);
        PointDeControleIG point2 = new PointDeControleIG(50.,60.,"id",etape);
        ArcIG arc = new ArcIG(point1, point2);
        assertEquals(arc.getPointDeControleArrivee(), point2);
    }
}
