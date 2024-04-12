package main.java.twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.twisk.monde.Monde;
import main.java.twisk.mondeIG.MondeIG;

public class EcouteurBoutonGuichet implements EventHandler<ActionEvent> {
    private MondeIG monde;
    public EcouteurBoutonGuichet(MondeIG monde){
        this.monde  = monde;
    }
    @Override
    public void handle(ActionEvent event) {
        this.monde.ajouter("guichet");
        monde.notifierObservateur();
    }
}
