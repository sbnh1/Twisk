package twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.monde.Monde;
import twisk.mondeIG.MondeIG;

public class EcouteurBoutonGuichet implements EventHandler<ActionEvent> {
    private MondeIG monde;

    /**
     * Constructeur de la classe EcouteurBoutonGuichet
     * @param monde le monde sur lequel l'événement est déclenché
     */
    public EcouteurBoutonGuichet(MondeIG monde){
        this.monde  = monde;
    }
    @Override
    public void handle(ActionEvent event) {
        this.monde.ajouter("guichet");
        monde.notifierObservateur();
    }
}
