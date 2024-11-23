package twiskIG;

import org.junit.jupiter.api.Test;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class testMondeIG {
    @Test
    public void testAjouter() {
        MondeIG monde = new MondeIG();
        monde.ajouter("activite");
        assertEquals(monde.getNbEtape(), 1);
    }

    @Test
    public void testAjouterPlus(){
        MondeIG monde = new MondeIG();
        monde.ajouter("act0");
        monde.ajouter("act1");
        monde.ajouter("act2");
        assertEquals(monde.getNbEtape(), 3);
    }

    @Test
    public void testGetNbEtape0(){
        MondeIG monde = new MondeIG();
        assertEquals(monde.getNbEtape(), 0);
    }
    @Test
    public void testGetNbEtape(){
        MondeIG monde = new MondeIG();
        monde.ajouter("Activite");
        monde.ajouter("activite");
        monde.ajouter("activite");
        assertEquals(monde.getNbEtape(), 3);
    }
    @Test
    public void testAjouterArc() throws TwiskException {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        PointDeControleIG pt1 = new PointDeControleIG(50, 50, "pt1", etape1);
        PointDeControleIG pt2 = new PointDeControleIG(150, 150, "pt2", etape2);
        monde.ajouter(pt1, pt2);
        assertEquals(1, monde.getNbArc());
    }

    @Test
    public void testAjouterMultipleArcs() throws TwiskException {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        PointDeControleIG pt1 = new PointDeControleIG(50, 50, "pt1", etape1);
        PointDeControleIG pt2 = new PointDeControleIG(150, 150, "pt2", etape2);
        monde.ajouter(pt1, pt2);
        PointDeControleIG pt3 = new PointDeControleIG(50, 150, "pt3", etape1);
        PointDeControleIG pt4 = new PointDeControleIG(150, 50, "pt4", etape2);
        monde.ajouter(pt3, pt4);
        assertEquals(2, monde.getNbArc());
    }

    @Test
    public void testIterator(){
        MondeIG monde = new MondeIG();
        monde.ajouter("act0");
        monde.ajouter("act1");
        monde.ajouter("act2");
        int count = 0;
        for (EtapeIG etape : monde) {
            count++;
        }
        assertEquals(3, count);
    }

/*
    @Test
    public void testString(){
        FabriqueIdentifiant instance = FabriqueIdentifiant.getInstance();
        instance.reset();
        MondeIG monde = new MondeIG();
        monde.ajouter("act0");
        monde.ajouter("act1");
        monde.ajouter("act2");
        monde.ajouter("act3");
        monde.ajouter("act4");
        monde.ajouter("act5");
        monde.ajouter("act6");
        monde.ajouter("act7");
        monde.ajouter("act8");
        monde.ajouter("act9");
        System.out.println(monde.toString());
    }
    */
    @Test
    public void testSupprimerEtape(){
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        monde.selectioneEtape(etape1);
        monde.selectioneEtape(etape2);
        monde.supprimerEtape(etape1, etape2);
        assertEquals(0, monde.getNbEtape());
    }
    @Test
    public void testSupprimerEtapePlus(){
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        EtapeIG etape3 = new ActiviteIG("act2", 150, 150);
        monde.selectioneEtape(etape1);
        monde.selectioneEtape(etape2);
        monde.selectioneEtape(etape3);
        monde.supprimerEtape(etape1, etape2, etape3);
        assertEquals(0, monde.getNbEtape());
    }
    @Test
    public void testDeselectionnerEtape() {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        EtapeIG etape3 = new ActiviteIG("act2", 150, 150);
        monde.selectioneEtape(etape1);
        monde.selectioneEtape(etape2);
        monde.selectioneEtape(etape3);

        monde.deselectionnerEtape(etape1);
        monde.deselectionnerEtape(etape3);

        assertFalse(monde.estSelectionneeEtape(etape1));
        assertTrue(monde.estSelectionneeEtape(etape2));
        assertFalse(monde.estSelectionneeEtape(etape3));
    }
    @Test
    public void testGetEtapesSelectionnees() {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        EtapeIG etape3 = new ActiviteIG("act2", 150, 150);
        monde.selectioneEtape(etape1);
        monde.selectioneEtape(etape2);
        monde.selectioneEtape(etape3);

        monde.deselectionnerEtape(etape1);
        monde.deselectionnerEtape(etape3);

        List<EtapeIG> etapesSelectionnees = monde.getEtapesSelectionnees();

        assertFalse(etapesSelectionnees.contains(etape1));
        assertTrue(etapesSelectionnees.contains(etape2));
        assertFalse(etapesSelectionnees.contains(etape3));
    }

    @Test
    public void testNbEtapeSelectionnees(){
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        EtapeIG etape3 = new ActiviteIG("act2", 150, 150);
        monde.selectioneEtape(etape1);
        monde.selectioneEtape(etape2);
        monde.selectioneEtape(etape3);

        assertEquals(monde.getNbEtapeSelectionner(), 3);
    }

    @Test
    public void testNbEtapeSelectionnees0(){
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        EtapeIG etape3 = new ActiviteIG("act2", 150, 150);
        monde.selectioneEtape(etape1);
        monde.selectioneEtape(etape2);
        monde.selectioneEtape(etape3);

        List<EtapeIG> etapesASupprimer = new ArrayList<>();
        for (EtapeIG etape : monde.getEtapesSelectionnees()) {
            etapesASupprimer.add(etape);
        }
        monde.supprimerEtape(etapesASupprimer.toArray(new EtapeIG[0]));

        assertEquals(monde.getNbEtapeSelectionner(), 0);
    }

    @Test
    public void testSupprimerArc() throws TwiskException {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        PointDeControleIG pt1 = new PointDeControleIG(0, 0, "pt1", etape1);
        PointDeControleIG pt2 = new PointDeControleIG(0, 0, "pt2", etape2);

        monde.ajouter(pt1, pt2);
        List<ArcIG> arcASupprimer = new ArrayList<>();
        for(ArcIG arcIG : monde.getArcs()){
            arcASupprimer.add(arcIG);
        }
        monde.supprimerArc(arcASupprimer.toArray(new ArcIG[0]));

        assertEquals(0, monde.getNbArc());
    }
    
    @Test
    public void testSupprimerArcPlus() throws TwiskException {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        EtapeIG etape2 = new ActiviteIG("act1", 200, 200);
        PointDeControleIG pt1 = new PointDeControleIG(0, 0, "pt1", etape1);
        PointDeControleIG pt2 = new PointDeControleIG(0, 0, "pt2", etape2);
        PointDeControleIG pt3 = new PointDeControleIG(0, 0, "pt3", etape2);

        monde.ajouter(pt1, pt2);
        monde.ajouter(pt1, pt3);
        List<ArcIG> arcASupprimer = new ArrayList<>();
        for(ArcIG arcIG : monde.getArcs()){
            if(arcIG.getPointDeControleDepart().getEtapeIG() == etape1){
                arcASupprimer.add(arcIG);
            }
        }
        monde.supprimerArc(arcASupprimer.toArray(new ArcIG[0]));

        assertEquals(0, monde.getNbArc());
    }
    @Test
    public void testRenommerEtape() {
        MondeIG monde = new MondeIG();
        EtapeIG etape1 = new ActiviteIG("act0", 100, 100);
        monde.selectioneEtape(etape1);
        monde.renommer("NouveauNom");
        assertEquals("NouveauNom", etape1.getNom());
    }

    @Test
    public void testSelectionnerArc() {
        MondeIG monde = new MondeIG();
        ArcIG arc = new ArcIG(null, null);
        monde.selectionnerArc(arc);
        assertTrue(monde.estSelectionneArc(arc));
    }

    @Test
    public void testDeselectionnerArc() {
        MondeIG monde = new MondeIG();
        ArcIG arc = new ArcIG(null, null);
        monde.selectionnerArc(arc);
        monde.deselectionnerArc(arc);
        assertFalse(monde.estSelectionneArc(arc));
    }

    @Test
    public void testViderSelectionArc() {
        MondeIG monde = new MondeIG();
        ArcIG arc1 = new ArcIG(null, null);
        ArcIG arc2 = new ArcIG(null, null);
        monde.selectionnerArc(arc1);
        monde.selectionnerArc(arc2);
        monde.viderSelectionArc();
        assertEquals(0, monde.getArcsSelectionnes().size());
    }

    @Test
    public void testSupprimerArcSelectionne() {
        MondeIG monde = new MondeIG();
        ArcIG arc1 = new ArcIG(null, null);
        ArcIG arc2 = new ArcIG(null, null);
        monde.selectionnerArc(arc1);
        monde.supprimerArcSelectionne();
        monde.selectionnerArc(arc2);
        assertFalse(monde.getArcsSelectionnes().contains(arc1));
        assertTrue(monde.getArcsSelectionnes().contains(arc2));
    }
}
