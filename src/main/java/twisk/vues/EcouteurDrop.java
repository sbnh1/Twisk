package main.java.twisk.vues;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import main.java.twisk.mondeIG.MondeIG;

public class EcouteurDrop implements EventHandler<DragEvent>{

    private MondeIG monde;
    private VueMondeIG vueMondeIG;

    /**
     * Constructeur d'un EcouteurDrop
     * @param monde Le MondeIG
     * @param vueMondeIG La VueMondeIG
     */
    public EcouteurDrop(MondeIG monde, VueMondeIG vueMondeIG){

        this.monde = monde;
        this.vueMondeIG = vueMondeIG;
    }

    @Override
    public void handle(DragEvent event){

        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if(dragboard.hasString()){

            this.monde.setPosEtapeIG(dragboard.getString(), (int)event.getX() - 90, (int)event.getY() - 60);
            this.monde.setPosPtEtapeIG(dragboard.getString());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
