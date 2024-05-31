package twisk.simulation;

import javafx.concurrent.Task;
import twisk.monde.*;
import twisk.mondeIG.*;
import twisk.exceptions.*;
import twisk.outils.*;
import twisk.vues.Observateur;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;

public class SimulationIG extends SujetObserve implements Observateur {

    private MondeIG mondeIG;
    private CorrespondanceEtapes correspondanceEtapes;
    private int nbClient;

    private Object instanceClassperso;
    private Monde monde;
    private GestionnaireClients gestionnaireClients;

    /**
     * Constructeur de SimulationIG
     * @param mondeIG monde que l'on va verifier et simuler
     */
    public SimulationIG(MondeIG mondeIG){
        this.mondeIG = mondeIG;
        this.ajouterObservateur(mondeIG);
        nbClient = 10;
    }

    /**
     * Simulation du monde
     */
    public void simuler() throws MondeInvalideException {
        this.verifierMonderIG();
        monde = this.creerMonde();
        Task<Void> simulation = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                lancerSimulation(monde, nbClient);
                return null;
            }
        };
        ThreadsManager.getInstance().startThread(simulation);
    }

    /**
     * stop la simulation
     */
    public void stoperSimulation(){
        try {
            ClassLoaderPerso classLoader = new ClassLoaderPerso(this.mondeIG.getClass().getClassLoader());
            Class<?> classePerso = classLoader.loadClass("twisk.simulation.Simulation");
            Constructor constructor = classePerso.getConstructor();
            Object instanceClassPerso = constructor.newInstance();
            Method tuerProcessus = classePerso.getMethod("tuerProcessus");
            tuerProcessus.invoke(instanceClassperso);
            ThreadsManager.getInstance().destroyThreads();
            FabriqueNumero.getInstance().resetNumeroEtape();
            FabriqueNumero.getInstance().resetNumeroSemaphore();
            notifierObservateur();
        }
        catch(Exception e){

        }
    }

    /**
     * Verification de la validité du monde
     */
    private void verifierMonderIG() throws MondeInvalideException {
        if (!this.mondeIG.aEntree()) {
            throw new MondeInvalideException("Erreur: il n'y a pas d'entree");
        }

        if (!this.mondeIG.aSortie()) {
            throw new MondeInvalideException("Erreur: il n'y a pas de sortie");
        }

        for (EtapeIG etape : this.mondeIG) {
            ArrayList<EtapeIG> successeurs = etape.getSuccesseurs();

            if(etape.estUneEntree()){
                if(verifierChemin(etape)){
                    throw new MondeInvalideException("Erreur: certaines entrée peuvent menées a une impasse");
                }
            }
            if (etape.estUnGuichet()) {
                if (etape.estUneSortie()) {
                    throw new MondeInvalideException("Erreur: un guichet ne peut etre une sortie");
                }
                if (etape.getNbSuccesseurs() > 1) {
                    throw new MondeInvalideException("Erreur: un guichet ne peut avoir qu'un seul successeur");
                }
                if (successeurs.get(0).estUnGuichet()) {
                    throw new MondeInvalideException("Erreur: un guichet ne peut avoir comme successeur un autre guichet");
                }
                ((ActiviteIG) successeurs.get(0)).setEstRestreinte(true);
                if (successeurs.get(0).estUneEntree()) {
                    throw new MondeInvalideException("Erreur: une Activité restreinte ne peut etre une entrée");
                }
                if (successeurs.get(0).getPredecesseurs().size() > 1) {
                    throw new MondeInvalideException("Erreur: une activité restreinte ne peut avoir qu'un seul predecesseur");
                }
            }
        }
    }

    /**
     * Vérifie si il y'a une sortie a chaque fin de chemin
     * Utilisation du DFS
     * @param etape l'étape de départ
     * @return true si le monde est juste, sinon false
     */
    public boolean verifierChemin(EtapeIG etape) {
        if (etape.estUneSortie()) {
            return false;
        }
        if (etape.getNbSuccesseurs() == 0) {
            return true;
        }
        for (EtapeIG successeur : etape.getSuccesseurs()) {
            if (verifierChemin(successeur)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retranscrit les étapeIG en étape
     * @param monde le monde où les étapes sont ajoutés.
     */
    public void ajouterEtapes(Monde monde) {
        for (EtapeIG etape : this.mondeIG) {
            if (etape.estUnGuichet()) {
                Etape guichet = new Guichet(etape.getNom(), etape.getNbJetons());
                this.correspondanceEtapes.ajouter(etape, guichet);
                monde.ajouter(guichet);
            } else if (etape.estUneActivite()) {
                if (((ActiviteIG) etape).estRestreinte()) {
                    Etape activiteRestreinte = new ActiviteRestreinte(etape.getNom(), etape.getDelai(), etape.getEcartTemps());
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

    /**
     * Fabrique un monde à partir du MondeIG
     * @return le monde créé
     */
    public Monde creerMonde() {
        FabriqueIdentifiant.getInstance().reset();
        FabriqueNumero.getInstance().resetNumeroEtape();
        FabriqueNumero.getInstance().resetNumeroEtape();
        Monde monde = new Monde();
        correspondanceEtapes = new CorrespondanceEtapes();
        ajouterEtapes(monde);

        this.ajouterSuccesseursEtapeIG();

        for (EtapeIG etapeIG : this.mondeIG) {
            Iterator<EtapeIG> iterator = etapeIG.iteratorSuccesseur();
            while (iterator.hasNext()) {
                Etape etape = this.correspondanceEtapes.get(etapeIG);
                Etape successeur = this.correspondanceEtapes.get(iterator.next());
                etape.ajouterSuccesseur(successeur);
            }

            if (etapeIG.estUneEntree()) {
                monde.aCommeEntree(this.correspondanceEtapes.get(etapeIG));
            }
            if (etapeIG.estUneSortie()) {
                monde.aCommeSortie(this.correspondanceEtapes.get(etapeIG));
            }
        }

        return monde;
    }

    /**
     * Renvoie le gestionnaire de clients
     * @return le gestionnaire de clients
     */
    public GestionnaireClients getGestionnaireClients() {
        return this.gestionnaireClients;
    }

    /**
     * Methode qui renvoie la CorrespondanceEtapes du MondeIG
     * @return La CorrespondanceEtapes
     */
    public CorrespondanceEtapes getCorrespondanceEtapes(){
        return this.correspondanceEtapes;
    }

    /**
     * Méthode qui prépare et lance la simulation du monde créé
     * @param monde le monde créé
     * @param nb le nombre de clients
     */
    public void lancerSimulation(Monde monde, int nb) {
        try {
            ClassLoaderPerso classLoader = new ClassLoaderPerso(this.getClass().getClassLoader());
            Class<?> classPerso = classLoader.loadClass("twisk.simulation.Simulation");
            Constructor<?> constructor = classPerso.getConstructor();
            instanceClassperso = constructor.newInstance();

            Method setNBClients = classPerso.getMethod("setNbClients", int.class);
            Method getGestionnaire = classPerso.getMethod("getGestionnaire");

            gestionnaireClients = (GestionnaireClients) getGestionnaire.invoke(instanceClassperso);
            Method simuler = classPerso.getMethod("simuler", Monde.class);

            Method ajouterObservateur = instanceClassperso.getClass().getMethod("ajouterObservateur", Observateur.class);
            ajouterObservateur.invoke(instanceClassperso, this);

            setNBClients.invoke(instanceClassperso, nb);
            simuler.invoke(instanceClassperso, monde);

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
     * Methode qui regarde si un ArcIG fait déjà le chemin demandé
     * @param pt1 Le premier PointDeControleIG
     * @param pt2 Le deuxième PointDeControleIG
     * @return true si le chemin existe déjà, false sinon
     */
    public boolean existChemin(PointDeControleIG pt1, PointDeControleIG pt2) {
        boolean exist = false;
        EtapeIG e1 = pt1.getEtapeIG();
        EtapeIG e2 = pt2.getEtapeIG();
        for (PointDeControleIG p1 : e1) {
            for (PointDeControleIG p2 : e2) {
                if (this.exist(new ArcIG(p1, p2))) {
                    exist = true;
                }
            }
        }
        return exist;
    }

    /**
     * Méthode qui vérifie si un ArcIG existe déjà
     * @param arcIG L'ArcIG à vérifier
     * @return True s'il existe déjà, faux sinon
     */
    public boolean exist(ArcIG arcIG) {
        boolean exist = false;
        for (ArcIG a : this.mondeIG.getArcs()) {
            if (a.equals(arcIG)) {
                exist = true;
            }
        }
        return exist;
    }

    /**
     * Méthode qui vérifie si deux EtapeIG sont reliées
     * @param e1 La première EtapeIG
     * @param e2 La deuxième EtapeIG
     * @return True si elles sont reliées, false sinon
     */
    private boolean sontReliees(EtapeIG e1, EtapeIG e2) {
        boolean reliees = false;
        for (PointDeControleIG p1 : e1) {
            for (PointDeControleIG p2 : e2) {
                if (existChemin(p1, p2)) {
                    reliees = true;
                }
            }
        }
        return reliees;
    }

    /**
     * Méthode qui ajoute les successeurs pour chaque EtapeIG du MondeIG
     */
    private void ajouterSuccesseursEtapeIG() {
        for (EtapeIG etape1 : this.mondeIG) {
            for (EtapeIG etape2 : this.mondeIG) {
                if (sontReliees(etape1, etape2)) {
                    etape1.ajouterSuccesseur(etape2);
                    etape2.ajouterPredecesseur(etape1);
                }
            }
        }
    }

    /**
     * Renvoie le nombre de clients
     * @return le nombre de clients
     */
    public int getNbClient() {
        return nbClient;
    }

    /**
     * Défini le nouveau nombre de clients
     * Nombre de clients défini à 5 si méthode non utilisé
     * @param nbClient le nouveau nombre de clients
     */
    public void setNbClient(int nbClient) {
        this.nbClient = nbClient;
    }

    @Override
    public void reagir() {
        this.gestionnaireClients = getGestionnaireClients();
        this.mondeIG.setGestionnaireClients(this.gestionnaireClients);
        this.mondeIG.setCorrespondanceEtapes(this.correspondanceEtapes);
        this.notifierObservateur();
    }
}
