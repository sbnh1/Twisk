package main.java.twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import main.java.twisk.mondeIG.MondeIG;
import javafx.scene.image.Image;


public class VueOutils extends Button implements Observateur{
    private MondeIG monde;
    private Tooltip tooltip;

    /**
     * Constructeur de la classe VueOutils
     * @param monde le MondeIG
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;
        tooltip = new Tooltip("Ajouter une activité");
        Tooltip.install(this, tooltip);
        //Image plus = new Image("/twisk/resources/images/plus.jpg");
        //this.setGraphic(new ImageView(plus));
        this.setOnAction(new EcouteurBouton(monde));
        this.setPrefSize(90, 30);
        this.setText("AJOUTER");
        this.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #367FFF;");

        this.monde.ajouterObservateur(this);
    }

    public void reagir(){

    }
}
