package twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.exceptions.MondeInvalideException;
import twisk.mondeIG.MondeIG;
import twisk.simulation.SimulationIG;

public class EcouteurBoutonSimulation implements EventHandler<ActionEvent> {

    private MondeIG mondeIG;
    private SimulationIG simulationIG;

    public EcouteurBoutonSimulation(MondeIG monde, SimulationIG simulation) {
        this.mondeIG = monde;
        this.simulationIG = simulation;
    }

    @Override
    public void handle(ActionEvent actionEvent){
        try{
            this.simulationIG.simuler();
        } catch (MondeInvalideException e) {
            System.out.println(e.getMessage());
        }
    }
}
