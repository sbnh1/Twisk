package twisk.simulation;

import twisk.monde.*;
import twisk.mondeIG.*;
import twisk.exceptions.*;
import twisk.outils.CorrespondanceEtapes;

import java.util.Iterator;

public class SimulationIG {

    private MondeIG monde;
    private CorrespondanceEtapes correspondanceEtapes;

    /**
     * Constructeur de SimulationIG
     * @param monde monde que l'on va verifier et simuler
     */
    public SimulationIG(MondeIG monde){
        this.monde = monde;
    }

    /**
     * Simulation du monde
     */
    public void simuler(){

    }

    /**
     * Verification de la validité du monde
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

    public void ajouterEtapes(Monde monde){

        for(EtapeIG etape : this.monde){
            if(etape.estUnGuichet()){
                Etape guichet = new Guichet(etape.getNom(), etape.getNbJetons());
                this.correspondanceEtapes.ajouter(etape, guichet);
                monde.ajouter(guichet);
            } else if (etape.estUneActivite()){
                if(((ActiviteIG)etape).estRestreinte()){
                    Etape activiteRestreinte = new ActiviteRestreinte(etape.getNom(),etape.getDelai(),etape.getEcartTemps());
                    this.correspondanceEtapes.ajouter(etape, activiteRestreinte);
                    monde.ajouter(activiteRestreinte);
                } else { // n'est pas une activité restreinte
                    Etape activite = new Activite(etape.getNom(), etape.getDelai(), etape.getEcartTemps());
                    this.correspondanceEtapes.ajouter(etape, activite);
                    monde.ajouter(activite);
                }
            }
        }

    }

    public Monde creerMonde(){
        Monde monde = new Monde();
        this.correspondanceEtapes = new CorrespondanceEtapes();
        ajouterEtapes(monde);

        for(EtapeIG etapeIG: this.monde){
            Iterator<EtapeIG> iterator = etapeIG.iteratorEtape();
            Etape etape = this.correspondanceEtapes.get(etapeIG);
            while(iterator.hasNext()){
                Etape successeur = this.correspondanceEtapes.get(iterator.next());
                etape.ajouterSuccesseur(successeur);
            }


            if(etapeIG.estUneEntree()){
                monde.aCommeEntree(this.correspondanceEtapes.get(etapeIG));
            }
            if(etapeIG.estUneSortie()){
                monde.aCommeSortie(this.correspondanceEtapes.get(etapeIG));
            }
        }

        return monde;
    }

}
