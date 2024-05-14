package twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.MondeIG;

public class EcouteurBoutonSimulation implements EventHandler<ActionEvent> {
    private MondeIG mondeIG;
    public EcouteurBoutonSimulation(MondeIG monde) {
        this.mondeIG = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("test");
    }
}
