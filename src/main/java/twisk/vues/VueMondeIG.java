package twisk.vues;

import javafx.scene.layout.Pane;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur{
    private MondeIG monde;


    /**
     * Constructeur de la classe VueMondeIG
     * @param monde le MondeIG
     */
    public VueMondeIG(MondeIG monde){
        super();
        this.monde = monde;
        this.monde.ajouterObservateur(this);
        this.setOnDragOver(new EcouteurDragOver(this.monde, this));
        this.setOnDragDropped(new EcouteurDrop(this.monde, this));
        initialiserVue();
    }

    /**
     * Initialise la vue du monde en affichant les activités et les arcs à chaque changement dans le MondeIG
     */
    private void initialiserVue() {
        Iterator<ArcIG> arcIterator = monde.arcIterator();
        while (arcIterator.hasNext()) {
            ArcIG arc = arcIterator.next();
            VueArcIG vueArc = new VueArcIG(arc, monde);
            getChildren().add(vueArc);
        }
        for (EtapeIG etape : monde) {
            if(etape.estUneActivite()) {
                VueActiviteIG vueActivite = new VueActiviteIG(monde, etape);
                getChildren().add(vueActivite);
                vueActivite.positionner(etape.getPosX(), etape.getPosY());
                vueActivite.taille(etape.getLargeur(), etape.getHauteur());
            } else {
                VueGuichetIG vueGuichet = new VueGuichetIG(monde, etape);
                getChildren().add(vueGuichet);
                vueGuichet.positionner(etape.getPosX(), etape.getPosY());
                vueGuichet.taille(etape.getLargeur(), etape.getHauteur());
            }


            for(PointDeControleIG point : etape.getPointsDeControle()){
                VuePointDeControleIG vuePoint = new VuePointDeControleIG(point, monde);
                getChildren().add(vuePoint);    
            }
        }
    }

    public void reagir(){
        getChildren().clear();
        initialiserVue();
    }
}