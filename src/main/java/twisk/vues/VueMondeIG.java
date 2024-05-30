package twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.monde.Etape;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.CorrespondanceEtapes;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;
import twisk.simulation.SimulationIG;

import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur{
    private MondeIG mondeIG;
    private GestionnaireClients gestionnaireClients;
    private CorrespondanceEtapes correspondanceEtapes;


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
/*
    public void initialiserCercle(){
        if(gestionnaireClients != null){
            for(Client client : gestionnaireClients){

                CorrespondanceEtapes ce = this.mondeIG.getCorres();
                Etape etape = client.getEtape();
                //if(!etape.estUneEntree() && !etape.estUneSortie()){

                if(etape != null){
                    EtapeIG etapeIG = ce.getEtapeIG(etape);
                    if(etape.estUnGuichet()){
                        for (Node node : this.getChildren()) {
                            if (node instanceof VueGuichetIG) {
                                VueGuichetIG vueGuichetIG = (VueGuichetIG)  node;
                                Label label = vueGuichetIG.getLabel(client.getRang());
                                Circle circle = new Circle(4);
                                circle.setFill(Color.RED);
                                HBox circleContainer = new HBox(circle);
                                circleContainer.setAlignment(Pos.CENTER);
                                label.setGraphic(circleContainer);
                            }
                        }
                    }
                }

            }
        }
    }*/

    public void initialiserCercle(){
        if(mondeIG.getGestionnaireClients() != null) {

            for(Client client : mondeIG.getGestionnaireClients()){

                Circle circle = new Circle(4.);
                circle.setFill(Color.RED);
                CorrespondanceEtapes ce = mondeIG.getCorrespondanceEtapes();
                Etape etape = client.getEtape();
                //if(!etape.estUneEntree() && !etape.estUneSortie()){

                    EtapeIG etapeIG = ce.getEtapeIG(etape);
                    if(etapeIG != null) {
                        if (etapeIG.estUneActivite()) {
                            System.out.println("faut faire ca : activité");
                        }
                        if (etapeIG.estUnGuichet()){
                            System.out.println("faut faire ca : guichet");
                            for (Node node : this.getChildren()) {
                                if (node instanceof VueGuichetIG) {
                                    VueGuichetIG vueGuichetIG = (VueGuichetIG)  node;
                                    Label label = vueGuichetIG.getLabel(client.getRang());

                                    HBox circleContainer = new HBox(circle);
                                    circleContainer.setAlignment(Pos.CENTER);
                                    label.setGraphic(circleContainer);
                                }
                            }

                        }

                    }
                //}
            }
        }
    }

    public void setGestionnaireClients(GestionnaireClients gestionnaireClients) {
        this.gestionnaireClients = gestionnaireClients;
    }

    public void setCorres(CorrespondanceEtapes correspondanceEtapes){
        this.correspondanceEtapes = correspondanceEtapes;
    }

    public void reagir(){
        this.setGestionnaireClients(this.mondeIG.getGestionnaireClients());
        getChildren().clear();
        initialiserVue();
        if(gestionnaireClients != null){
            initialiserCercle();
        }
    }
}