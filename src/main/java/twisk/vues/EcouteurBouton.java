package main.java.twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.twisk.mondeIG.MondeIG;

public class EcouteurBouton implements EventHandler<ActionEvent> {
    private MondeIG monde;

    /**
     * Constructeur de la classe EcouteurBouton.
     * @param monde le monde sur lequel l'événement est déclenché
     */
    public EcouteurBouton(MondeIG monde) {
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent event) {
       this.monde.ajouter("activite");
       monde.notifierObservateur();
    }
}
