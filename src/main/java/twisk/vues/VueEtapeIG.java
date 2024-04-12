package main.java.twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.java.twisk.mondeIG.EtapeIG;
import main.java.twisk.mondeIG.MondeIG;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class VueEtapeIG extends VBox implements Observateur {
    private MondeIG monde;
    private EtapeIG etape;
    public Label label;
    private Label entreSortie;


    /**
     * Constructeur de la classe VueEtapeIG
     * @param monde le MondeIG
     * @param etape l'EtapeIG
     */
    public VueEtapeIG(MondeIG monde, EtapeIG etape){
        this.etape = etape;
        this.monde = monde;
        if(etape.estUneActivite()){
            this.label = new Label(etape.getNom() + " : " + this.etape.getDelai() + " + " + this.etape.getEcartTemps() + " temps");
        } else {
            if(etape.getNbJetons() > 1){
                this.label = new Label(etape.getNom() + " : " + this.etape.getNbJetons() + " jetons");
            } else {
                this.label = new Label(etape.getNom() + " : " + this.etape.getNbJetons() + " jeton");
            }
        }
        label.setStyle("-fx-text-fill: black;");
        this.setId(this.etape.getIdentifiant());
        this.setOnDragDetected(new EcouteurDrag(this.monde, this));

        entreSortie = new Label("");
        if (etape.estUneEntree() && etape.estUneSortie()) {
            entreSortie.setText("ES");
        } else if (etape.estUneSortie()) {
            entreSortie.setText("S");
        } else if (etape.estUneEntree()){
            entreSortie.setText("E");
        }
        entreSortie.setStyle("-fx-text-fill: red;");;
        this.getChildren().addAll(label, entreSortie);
        setAlignment(Pos.TOP_CENTER);
        if(monde.estSelectionneeEtape(etape)){
            // quand l'étape est selectioner
            this.setStyle("-fx-border-color: red; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        } else {
            // quand l'étape n'est pas selectionner
            this.setStyle("-fx-border-color: #0059FF; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px;");
        }


        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (monde.estSelectionneeEtape(etape)) {
                    monde.deselectionnerEtape(etape);
                    monde.notifierObservateur();
                } else {
                    monde.selectioneEtape(etape);
                    monde.notifierObservateur();
                }
            }
        });
    }

}
