package twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.monde.Etape;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;

import java.util.Iterator;
import java.util.Random;

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


    /**
     * Initialise la position des cercles dans les étapes
     */
    public void initialiserCercle(){
        if(mondeIG.getGestionnaireClients() != null) {

            for(Client client : mondeIG.getGestionnaireClients()){

                Circle circle = new Circle(4.);
                choixCouleur(circle, client);
                CorrespondanceEtapes ce = mondeIG.getCorrespondanceEtapes();
                Etape etape = client.getEtape();
                    EtapeIG etapeIG = ce.getEtapeIG(etape);
                    if (etapeIG != null) {
                    if (etapeIG.estUneActivite()) {
                        for (Node node : this.getChildren()) {
                            if (node instanceof VueActiviteIG) {
                                VueActiviteIG vueActiviteIG = (VueActiviteIG) node;
                                if (vueActiviteIG.getEtapeIG().equals(etapeIG)) {  // Vérifie que c'est la bonne étape
                                    HBox hbox = vueActiviteIG.getHbox();

                                    //Determine la position du client dans l'activité à chaque tour de boucle
                                    Random random = new Random();
                                    double maxPositionX = (double) TailleComposants.getInstance().activiteLargeur / 2;
                                    double randomPositionX = random.nextDouble() * maxPositionX;

                                    double maxPositionY = (double) TailleComposants.getInstance().activiteHauter / 2;
                                    double randomPositionY = random.nextDouble() * maxPositionY;


                                    VBox circleContainer = new VBox(circle);
                                    circleContainer.setTranslateX(randomPositionX);
                                    circleContainer.setTranslateY(randomPositionY);

                                    hbox.getChildren().add(circleContainer);
                                }
                            }
                        }
                    } else if (etapeIG.estUnGuichet()) {
                        for (PointDeControleIG pointDeControle : etapeIG) {
                            if(pointDeControle.getSensCirculation()) {//a un sens de circulation  = true
                                if(pointDeControle.getIdentifiant().startsWith("PCG")){ // regarde si la fleche d'entrée des clients est a droite ou à gauche
                                    for (Node node : this.getChildren()) {
                                        if (node instanceof VueGuichetIG) {
                                            VueGuichetIG vueGuichetIG = (VueGuichetIG) node;
                                            if (vueGuichetIG.getEtapeIG().equals(etapeIG)) {//vérifie que c'est la bonne étape
                                                Label label = vueGuichetIG.getLabel(client.getRang() % 10);
                                                HBox circleContainer = new HBox(circle);
                                                circleContainer.setAlignment(Pos.CENTER);
                                                label.setGraphic(circleContainer);
                                            }
                                        }
                                    }
                                } else {
                                    for (Node node : this.getChildren()) {
                                        if (node instanceof VueGuichetIG) {
                                            VueGuichetIG vueGuichetIG = (VueGuichetIG) node;
                                            if (vueGuichetIG.getEtapeIG().equals(etapeIG)) {//vérifie que c'est la bonne étape
                                                int rang = client.getRang() % 10;
                                                rang = 9 - rang;
                                                Label label = vueGuichetIG.getLabel(rang);
                                                HBox circleContainer = new HBox(circle);
                                                circleContainer.setAlignment(Pos.CENTER);
                                                label.setGraphic(circleContainer);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    }
                //}
            }
        }
    }

    /**
     * Défini le gestionnaire avec celui donné en paramètre
     * @param gestionnaireClients le gestionnaire à copier
     */
    public void setGestionnaireClients(GestionnaireClients gestionnaireClients) {
        this.gestionnaireClients = gestionnaireClients;
    }



    /**
     * Donne une nouvelle couleur à un cercle
     * @param circle à qui choisir la couleur
     **/
    public void choixCouleur(Circle circle, Client client){
        switch (client.getCouleur()) {
            case 0:
                circle.setFill(Color.RED);
                break;
            case 1:
                circle.setFill(Color.BLUE);
                break;
            case 2:
                circle.setFill(Color.GREEN);
                break;
            case 3:
                circle.setFill(Color.DEEPPINK);
            default:
                circle.setFill(Color.DEEPPINK);
        }
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