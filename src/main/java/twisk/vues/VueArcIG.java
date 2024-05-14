package twisk.vues;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import twisk.mondeIG.ArcIG;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.MondeIG;

public class VueArcIG extends Pane implements Observateur{
    private ArcIG arc;
    private MondeIG monde;

    /**
     * Constructeur de la classe VueArcIG
     * @param arc l'ArcIG
     * @param monde le MondeIG
     */
    public VueArcIG(ArcIG arc, MondeIG monde) {
        this.arc = arc;
        this.monde = monde;
        dessinerArc();
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (monde.estSelectionneArc(arc)) {
                    monde.deselectionnerArc(arc);
                    dessinerArc();
                    monde.notifierObservateur();
                } else {
                    monde.selectionnerArc(arc);
                    dessinerArc();
                    monde.notifierObservateur();
                }
            }
        });
    }


    /**
     * Méthode qui permet de dessiner tout les arcs du MondeIG
     */
    private void dessinerArc() {
        Line ligne = new Line();
        ligne.setStartX(arc.getPointDeControleDepart().getPositionX());
        ligne.setStartY(arc.getPointDeControleDepart().getPositionY());
        ligne.setEndX(arc.getPointDeControleArrivee().getPositionX());
        ligne.setEndY(arc.getPointDeControleArrivee().getPositionY());

        // Calcul de l'angle de la ligne
        double angle = Math.atan2(ligne.getEndY() - ligne.getStartY(), ligne.getEndX() - ligne.getStartX());

        // Calcul des coordonnées pour orienter le triangle par rapport à la direction de la ligne
        double arrowLength = 10; // Longueur de la flèche
        double arrowWidth = 5; // Largeur de la flèche
        double x1 = ligne.getEndX();
        double y1 = ligne.getEndY();
        double x2 = x1 - arrowLength * Math.cos(angle) + arrowWidth * Math.sin(angle);
        double y2 = y1 - arrowLength * Math.sin(angle) - arrowWidth * Math.cos(angle);
        double x3 = x1 - arrowLength * Math.cos(angle) - arrowWidth * Math.sin(angle);
        double y3 = y1 - arrowLength * Math.sin(angle) + arrowWidth * Math.cos(angle);

        Polyline triangle = new Polyline();
        triangle.getPoints().addAll(new Double[]{x2, y2, x1, y1, x3, y3});

        if(monde.estSelectionneArc(arc)){
            ligne.setStrokeWidth(2);
            triangle.setStrokeWidth(2);
            ligne.setStroke(Color.RED);
            triangle.setStroke(Color.RED);
        } else {
            ligne.setStrokeWidth(1);
            triangle.setStrokeWidth(1);
            ligne.setStroke(Color.BLACK);
            triangle.setStroke(Color.BLACK);
        }

        getChildren().addAll(ligne, triangle);
    }

    @Override
    public void reagir() {

    }
}
