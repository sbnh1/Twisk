package twisk.simulation;

import twisk.monde.Monde;
import twisk.mondeIG.*;
import twisk.exceptions.*;

public class SimulationIG {

    private MondeIG monde;

    /**
     * Constructeur de SimulationIG
     * @param monde monde que l'on va verifier et simuler
     */
    public SimulationIG(MondeIG monde){
        this.monde = monde;
    }

    /**
     * simulation du monde
     */
    public void simuler(){

    }

    /**
     * verification de la validité du monde
     */
    private void verifierMonderIG()throws MondeInvalideException{
        if(!this.monde.aEntree()){
            throw new MondeInvalideException("Erreur: il n'y a pas d'entree");
        }
        if(!this.monde.aSortie()){
            throw new MondeInvalideException("Erreur: il n'y a pas de sortie");
        }
        while(this.monde.arcIterator().hasNext()){
            ArcIG arcIG = this.monde.arcIterator().next();
            EtapeIG etapeDepart = arcIG.getPointDeControleDepart().getEtapeIG();
            EtapeIG etapeArrivee = arcIG.getPointDeControleArrivee().getEtapeIG();
            if(etapeDepart.estUnGuichet() && etapeArrivee.estUneActivite()){
                ActiviteIG activite = (ActiviteIG)etapeArrivee;
                if(!activite.estRestreinte()){
                    throw new MondeInvalideException("Erreur: Certains guichets ne sont pas suivis d'une activité restreinte");
                }
            }
        }
    }

    private Monde creerMonde(){
        Monde monde = new Monde();
        return monde;
    }

}
