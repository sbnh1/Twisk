package twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import org.junit.jupiter.api.DisplayNameGenerator;
import twisk.mondeIG.MondeIG;
import javafx.scene.image.Image;
import twisk.simulation.SimulationIG;


public class VueOutils extends ToolBar implements Observateur{
    private MondeIG monde;
    private Tooltip tooltip;
    private SimulationIG simulation;
    /**
     * Constructeur de la classe VueOutils
     * @param monde le MondeIG
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;

        this.simulation = new SimulationIG(monde);

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
        boutonActivite.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #1e847f;");

        Button boutonGuichet = new Button("GUICHET");
        Tooltip tooltipGuichet = new Tooltip("Ajouter un guichet");
        Tooltip.install(boutonGuichet, tooltipGuichet);
        boutonGuichet.setOnAction(new EcouteurBoutonGuichet(monde));
        boutonGuichet.setPrefSize(90,30);
        boutonGuichet.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #1e847f;");

        Button boutonSimulation = new Button("SIMULATION");
        Tooltip tooltipSimulation = new Tooltip("Lance la simulation du monde");
        Tooltip.install(boutonSimulation, tooltipSimulation);
        boutonSimulation.setOnAction(new EcouteurBoutonSimulation(monde, simulation));
        boutonSimulation.setPrefSize(110,30);
        boutonSimulation.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #c66b3d;");

        this.setStyle("-fx-background-color: #e5e5dc;");

        this.getItems().addAll(espaceGauche ,boutonActivite, boutonGuichet, boutonSimulation, espaceDroite);
        this.monde.ajouterObservateur(this);
    }

    public void reagir(){

    }
}
