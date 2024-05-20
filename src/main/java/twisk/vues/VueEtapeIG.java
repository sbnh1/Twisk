package twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
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
            this.setStyle("-fx-border-color: #FF0000;-fx-border-width: 3px;-fx-background-color: #DEDEDE;-fx-border-radius: 5.0;");
        } else {
            // quand l'étape n'est pas selectionner
            this.setStyle("-fx-border-color: #0000FF;-fx-border-width: 3px;-fx-background-color: #DEDEDE;-fx-border-radius: 5.0;");
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
