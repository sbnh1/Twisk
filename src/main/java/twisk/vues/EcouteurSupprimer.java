package main.java.twisk.vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.java.twisk.mondeIG.ArcIG;
import main.java.twisk.mondeIG.EtapeIG;
import main.java.twisk.mondeIG.MondeIG;

import java.util.ArrayList;
import java.util.List;

public class EcouteurSupprimer implements EventHandler<ActionEvent> {
    private MondeIG monde;

    /**
     * Constructeur d'un EcouteurSupprimer
     * @param monde le mondeIG
     */
    public EcouteurSupprimer(MondeIG monde){
        this.monde = monde;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //parcours de la liste des étapes sélectionnées
        List<EtapeIG> etapesASupprimer = new ArrayList<>();
        for (EtapeIG etape : monde.getEtapesSelectionnees()) {
            //parcours de tous les arcs du monde
            List<ArcIG> arcASupprimer = new ArrayList<>();
            for(ArcIG arcIG : monde.getArcs()){
                //vérification que l'arc appartient à l'étape à supprimer
                if (arcIG.getPointDeControleDepart().getEtapeIG() == etape || arcIG.getPointDeControleArrivee().getEtapeIG() == etape) {
                    arcASupprimer.add(arcIG);
                }
            }
                monde.supprimerArc(arcASupprimer.toArray(new ArcIG[0]));
            etapesASupprimer.add(etape);
        }
        monde.supprimerEtape(etapesASupprimer.toArray(new EtapeIG[0]));
        for(ArcIG arcIG : monde.getArcsSelectionnes()){
            monde.supprimerArc(arcIG);
        }
    }

}
