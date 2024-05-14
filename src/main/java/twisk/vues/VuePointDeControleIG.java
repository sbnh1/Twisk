package twisk.vues;

import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.mondeIG.PointDeControleIG;
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
        if(pointDeControleIG.estSelectionee()){
            setFill((Color.RED));
        } else {
            this.setFill(Color.BLACK);
        }
        this.monde = monde;

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    monde.pointDeControleSelectionne(pointDeControleIG);
                    monde.notifierObservateur();
                    //setFill((Color.RED));
                } catch (TwiskException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
