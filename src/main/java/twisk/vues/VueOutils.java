package twisk.vues;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import twisk.mondeIG.MondeIG;
import twisk.simulation.Simulation;
import twisk.simulation.SimulationIG;

import java.util.Optional;


public class VueOutils extends ToolBar implements Observateur{
    private MondeIG monde;
    private Tooltip tooltip;
    private SimulationIG simulationIG;
    private Simulation simulation;
    /**
     * Constructeur de la classe VueOutils
     * @param monde le MondeIG
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;

        this.simulationIG = new SimulationIG(monde);

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


        Button boutonSimulation = new Button("Start");
        Tooltip tooltipSimulation = new Tooltip("Lancer la simulation du monde");
        Tooltip.install(boutonSimulation, tooltipSimulation);
        boutonSimulation.setOnAction(new EcouteurBoutonSimulation(monde, simulationIG, boutonSimulation));
        boutonSimulation.setPrefSize(110,30);
        boutonSimulation.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #c66b3d;");

        Button boutonNbClients = new Button("Clients");
        Tooltip tooltipClients = new Tooltip("Choisir le nombre de clients");
        Tooltip.install(boutonNbClients, tooltipClients);
        boutonNbClients.setOnAction(event -> defNbClient());
        boutonNbClients.setPrefSize(110,30);
        boutonNbClients.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #c66b3d;");

        this.setStyle("-fx-background-color: #e5e5dc;");

        this.getItems().addAll(espaceGauche ,boutonNbClients ,boutonActivite, boutonGuichet, boutonSimulation, espaceDroite);
        this.monde.ajouterObservateur(this);
    }

    private void defNbClient() {
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Définir le nombre de clients");
        text.setHeaderText(null);
        text.setContentText("Entrez le nouveau nombre de clients : ");
        Optional<String> result = text.showAndWait();

        // convertions en entier
        result.ifPresent(input -> {
            try {
                int clients = Integer.parseInt(input);
                this.simulationIG.setNbClient(clients);
                //this.simulation.setNbClients(clients);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un entier valide.");
                alert.showAndWait();
            }
        });
    }

    public void reagir(){

    }
}
