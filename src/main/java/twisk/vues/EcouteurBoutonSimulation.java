package twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import twisk.exceptions.MondeInvalideException;
import twisk.mondeIG.MondeIG;
import twisk.simulation.SimulationIG;

import java.awt.*;

public class EcouteurBoutonSimulation implements EventHandler<ActionEvent> {

    private MondeIG mondeIG;
    private SimulationIG simulationIG;
    private Button bouton;

    public EcouteurBoutonSimulation(MondeIG monde, SimulationIG simulation, Button bouton) {
        this.mondeIG = monde;
        this.simulationIG = simulation;
        this.bouton = bouton;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Tooltip infoBulleSimulation;
            if(this.bouton.getText().equals("Start")){
                this.simulationIG.simuler();
                this.bouton.setText("Stop");
                infoBulleSimulation = new Tooltip("Arrêter la simulation du monde");
                Tooltip.install(this.bouton, infoBulleSimulation);
            }else{
                this.bouton.setText("Start");
                infoBulleSimulation = new Tooltip("Lancer la simulation du monde");
                Tooltip.install(this.bouton, infoBulleSimulation);
            }
        } catch (MondeInvalideException e) {
            System.out.println(e.getMessage());
        }
    }
}
