package twisk.simulation;

import javafx.concurrent.Task;
import twisk.monde.*;
import twisk.mondeIG.*;
import twisk.exceptions.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.ThreadsManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public void simuler() throws MondeInvalideException {
        this.verifierMonderIG();
        Monde monde = this.creerMonde();
        Task<Void> simulation = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                lancerSimulation(monde, 5);
                return null;
            }
        };
        ThreadsManager.getInstance().startThread(simulation);
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

        Iterator<ArcIG> arcIterator = this.monde.arcIterator();
        while(arcIterator.hasNext()){
            ArcIG arcIG = arcIterator.next();
            EtapeIG etapeDepart = arcIG.getPointDeControleDepart().getEtapeIG();
            EtapeIG etapeArrivee = arcIG.getPointDeControleArrivee().getEtapeIG();
            if(etapeDepart.estUnGuichet() && etapeArrivee.estUneActivite()){
                ((ActiviteIG) etapeArrivee).setEstRestreinte(true);
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

        this.ajouterSuccesseursEtapeIG();

        for(EtapeIG etapeIG: this.monde){
            Iterator<EtapeIG> iterator = etapeIG.iteratorEtape();
            while(iterator.hasNext()){
                Etape etape = this.correspondanceEtapes.get(etapeIG);
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

    public void lancerSimulation(Monde monde, int nb) {
        try {
            ClassLoaderPerso classLoader = new ClassLoaderPerso(this.getClass().getClassLoader());
            Class<?> classPerso = classLoader.loadClass("twisk.simulation.Simulation");
            Constructor<?> constructor = classPerso.getConstructor();
            Object instanceClassperso = constructor.newInstance();
            Method setNBClient_ = classPerso.getMethod("setNbClients", int.class);
            Method simuler_ = classPerso.getMethod("simuler", Monde.class);
            setNBClient_.invoke(instanceClassperso, nb);
            simuler_.invoke(instanceClassperso, monde);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode qui regarde si un ArcIG fait deja le chemin demande
     * @param pt1 Le premier PointDeControleIG
     * @param pt2 Le deuxieme PointDeControleIG
     * @return true si le chemin existe deja, false sinon
     */
    public boolean existChemin(PointDeControleIG pt1, PointDeControleIG pt2){

        boolean exist = false;
        EtapeIG e1 = pt1.getEtapeIG();
        EtapeIG e2 = pt2.getEtapeIG();
        for(PointDeControleIG p1 : e1){

            for(PointDeControleIG p2 : e2){

                if(this.exist(new ArcIG(p1, p2))){

                    exist = true;
                }
            }
        }

        return exist;
    }

    /**
     * Methode qui verifie si un ArcIG existe deja
     * @param arcIG L'ArcIG a verifier
     * @return True si il existe deja, faux sinon
     */
    public boolean exist(ArcIG arcIG){

        boolean exist = false;
        for(ArcIG a : this.monde.getArcs()){

            if(a.equals(arcIG)){

                exist = true;
            }
        }
        return exist;
    }


    /**
     * Methode qui verifie si deux EtapeIG sont reliees
     * @param e1 La premiere EtapeIG
     * @param e2 La deuxieme EtapeIG
     * @return True si elles sont reliees, false sinon
     */
    private boolean sontReliees(EtapeIG e1, EtapeIG e2){

        boolean reliees = false;
        for(PointDeControleIG p1 : e1){

            for(PointDeControleIG p2 : e2){

                if(existChemin(p1, p2)){

                    reliees = true;
                }
            }
        }
        return reliees;
    }

    /**
     * Methode qui ajoute les successeurs pour chaque EtapeIG du MondeIG
     */
    private void ajouterSuccesseursEtapeIG(){

        for(EtapeIG etape1 : this.monde){

            for(EtapeIG etape2 : this.monde){

                if(sontReliees(etape1, etape2)){

                    etape1.ajouter(etape2);
                }
            }
        }
    }
}
