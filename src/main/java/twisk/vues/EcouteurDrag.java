package twisk.vues;

import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.input.Dragboard;
import twisk.mondeIG.MondeIG;

public class EcouteurDrag implements EventHandler<MouseEvent>{

    private MondeIG monde;
    private VueEtapeIG vueEtapeIG;

    /**
     * Constructeur d'un EcouteurDrag
     * @param monde Le MondeIG
     * @param vueEtapeIG La VueEtapeIG
     */
    public EcouteurDrag(MondeIG monde, VueEtapeIG vueEtapeIG){
        this.monde = monde;
        this.vueEtapeIG = vueEtapeIG;
    }

    @Override
    public void handle(MouseEvent event){
        Dragboard dragboard = this.vueEtapeIG.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        WritableImage capture = this.vueEtapeIG.snapshot(null, null);
        content.putImage(capture);
        content.putString(this.vueEtapeIG.getId());
        dragboard.setContent(content);
        event.consume();
    }
}
