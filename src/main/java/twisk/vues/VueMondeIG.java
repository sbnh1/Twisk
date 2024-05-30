package twisk.vues;

import javafx.scene.layout.Pane;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;
import twisk.simulation.SimulationIG;

import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur{
    private MondeIG mondeIG;
    private GestionnaireClients gestionnaireClients;


    /**
     * Constructeur de la classe VueMondeIG
     * @param monde le MondeIG
     */
    public VueMondeIG(MondeIG monde){
        super();
        this.mondeIG = monde;
        this.mondeIG.ajouterObservateur(this);
        this.setGestionnaireClients(this.mondeIG.getGestionnaireClients());
        this.setOnDragOver(new EcouteurDragOver(this.mondeIG, this));
        this.setOnDragDropped(new EcouteurDrop(this.mondeIG, this));
        initialiserVue();
    }

    /**
     * Initialise la vue du monde en affichant les activités et les arcs à chaque changement dans le MondeIG
     */
    private void initialiserVue() {
        Iterator<ArcIG> arcIterator = mondeIG.arcIterator();
        while (arcIterator.hasNext()) {
            ArcIG arc = arcIterator.next();
            VueArcIG vueArc = new VueArcIG(arc, mondeIG);
            getChildren().add(vueArc);
        }
        for (EtapeIG etape : mondeIG) {
            if(etape.estUneActivite()) {
                VueActiviteIG vueActivite = new VueActiviteIG(mondeIG, etape);
                getChildren().add(vueActivite);
                vueActivite.positionner(etape.getPosX(), etape.getPosY());
                vueActivite.taille(etape.getLargeur(), etape.getHauteur());
            } else {
                VueGuichetIG vueGuichet = new VueGuichetIG(mondeIG, etape);
                getChildren().add(vueGuichet);
                vueGuichet.positionner(etape.getPosX(), etape.getPosY());
                vueGuichet.taille(etape.getLargeur(), etape.getHauteur());
            }


            for(PointDeControleIG point : etape.getPointsDeControle()){
                VuePointDeControleIG vuePoint = new VuePointDeControleIG(point, mondeIG);
                getChildren().add(vuePoint);    
            }
        }
    }

    public void initialiserCercle(){
        System.out.println(" ");
    }

    public void setGestionnaireClients(GestionnaireClients gestionnaireClients) {
        this.gestionnaireClients = gestionnaireClients;
    }

    public void reagir(){
        this.setGestionnaireClients(this.mondeIG.getGestionnaireClients());

        getChildren().clear();
        initialiserVue();
        initialiserCercle();
    }
}