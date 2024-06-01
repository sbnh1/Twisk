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
    private SimulationIG simulationIG;
    private int nbClientBouton;
    private Button boutonNbClients;
    private Button boutonChoixLoi;
    /**
     * Constructeur de la classe VueOutils
     * @param monde le MondeIG
     */
    public VueOutils(MondeIG monde){
        super();
        this.monde = monde;

        this.simulationIG = new SimulationIG(monde);
        this.nbClientBouton = this.simulationIG.getNbClient();

        Region espaceGauche = new Region();
        HBox.setHgrow(espaceGauche, Priority.ALWAYS);

        Region espaceDroite = new Region();
        HBox.setHgrow(espaceDroite, Priority.ALWAYS);

        Button boutonActivite = new Button("ACTIVITE");
        Tooltip tooltipActivite = new Tooltip("Ajouter une activité");
        Tooltip.install(boutonActivite, tooltipActivite);
        boutonActivite.setOnAction(new EcouteurBouton(monde));
        boutonActivite.setPrefSize(90, 30);
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

        boutonNbClients = new Button("Clients : " + nbClientBouton);
        Tooltip tooltipClients = new Tooltip("Choisir le nombre de clients");
        Tooltip.install(boutonNbClients, tooltipClients);
        boutonNbClients.setOnAction(event -> defNbClient());
        boutonNbClients.setPrefSize(110,30);
        boutonNbClients.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #c66b3d;");

        boutonChoixLoi = new Button("Loi : " + simulationIG.getChoixLoi());
        Tooltip tooltipChoixLoi = new Tooltip("Choisir la loi d'entrée que vous voulez");
        Tooltip.install(boutonChoixLoi, tooltipChoixLoi);
        boutonChoixLoi.setOnAction(event -> defChoixLoi());
        boutonChoixLoi.setPrefSize(110,30);
        boutonChoixLoi.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000;-fx-background-color: #9E7BB5;");

        this.setStyle("-fx-background-color: #e5e5dc;");

        this.getItems().addAll(espaceGauche, boutonChoixLoi, boutonActivite, boutonGuichet, boutonNbClients, boutonSimulation, espaceDroite);
        this.monde.ajouterObservateur(this);
    }

    /**
     * Défini la loi d'entrée des clients
     */
    private void defChoixLoi() {
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Loi à choisir");
        text.setHeaderText(null);
        text.setContentText("Loi : \n" +
                "  - Loi Uniforme (1)\n" +
                "  - Loi Gauss (2)\n" +
                "  - Loi Exponentiel (3)\n" +
                "\n  tappez le nombre correspondant à la loi voulu : ");
        Optional<String> result = text.showAndWait();

        // convertions en entier
        result.ifPresent(input -> {
            try {
                int choixLoi = Integer.parseInt(input);
                this.simulationIG.setChoixLoi(choixLoi);
                if(choixLoi > 2){
                    boutonChoixLoi.setText("Loi : 3");
                } else {
                    boutonChoixLoi.setText("Loi : " + choixLoi);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir un entier valide.");
                alert.showAndWait();
            }
        });
    }

    /**
     * Défini le nombre de clients dans la simulation
     */
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
                boutonNbClients.setText("Clients : " + clients);
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
