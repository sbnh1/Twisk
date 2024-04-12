package main.java.twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import main.java.twisk.mondeIG.MondeIG;
import javafx.scene.image.Image;


public class VueOutils extends ToolBar implements Observateur{
    private MondeIG monde;
    private Tooltip tooltip;

    /**
     * Constructeur de la classe VueOutils
     * @param monde le MondeIG
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;

        Region espaceGauche = new Region();
        HBox.setHgrow(espaceGauche, Priority.ALWAYS);

        // Créer un espace vide à droite
        Region espaceDroite = new Region();
        HBox.setHgrow(espaceDroite, Priority.ALWAYS);

        Button boutonActivite = new Button("ACTIVITE");
        Tooltip tooltipActivite = new Tooltip("Ajouter une activité");
        Tooltip.install(boutonActivite, tooltipActivite);
        boutonActivite.setOnAction(new EcouteurBouton(monde));
        boutonActivite.setPrefSize(90, 30);
        //boutonActivite.setText("AJOUTER");
        boutonActivite.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #367FFF;");

        Button boutonGuichet = new Button("GUICHET");
        Tooltip tooltipGuichet = new Tooltip("Ajouter un guichet");
        Tooltip.install(boutonGuichet, tooltipGuichet);
        boutonGuichet.setOnAction(new EcouteurBoutonGuichet(monde));
        boutonGuichet.setPrefSize(90,30);
        boutonGuichet.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #367FFF;");

        this.getItems().addAll(espaceGauche ,boutonActivite, boutonGuichet, espaceDroite);
        this.monde.ajouterObservateur(this);
    }

    public void reagir(){

    }
}
