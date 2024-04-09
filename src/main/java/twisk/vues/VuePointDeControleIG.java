package main.java.twisk.vues;

import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.java.twisk.exceptions.TwiskException;
import main.java.twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import main.java.twisk.mondeIG.PointDeControleIG;
import javafx.scene.input.MouseEvent;


public class VuePointDeControleIG extends Circle {
    private MondeIG monde;

    /**
     * Constructeur de la classe VuePointDeControleIG
     * @param pointDeControleIG le PointDeControleIG
     * @param monde le MondeIG
     */
    public VuePointDeControleIG(PointDeControleIG pointDeControleIG, MondeIG monde){
        super(pointDeControleIG.getPositionX(), pointDeControleIG.getPositionY(), TailleComposants.getInstance().rayonCercle);
        this.setFill(Color.BLACK);
        this.monde = monde;

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    monde.pointDeControleSelectionne(pointDeControleIG);
                    setFill((Color.RED));
                } catch (TwiskException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
