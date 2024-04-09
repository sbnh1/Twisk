package main.java.twisk.vues;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import main.java.twisk.mondeIG.MondeIG;

public class EcouteurDragOver implements EventHandler<DragEvent>{

    private MondeIG monde;
    private VueMondeIG vueMondeIG;

    /**
     * Constructeur d'un EcouteurDragOver
     * @param monde Le MondeIG
     * @param vueMondeIG La VueMondeIG
     */
    public EcouteurDragOver(MondeIG monde, VueMondeIG vueMondeIG){

        this.monde = monde;
        this.vueMondeIG = vueMondeIG;
    }

    @Override
    public void handle(DragEvent event){

        Dragboard dragboard = event.getDragboard();
        if(event.getGestureSource() != this.vueMondeIG && dragboard.hasString()){
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }
}
