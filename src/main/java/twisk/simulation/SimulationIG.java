package twisk.simulation;

import javafx.concurrent.Task;
import twisk.monde.*;
import twisk.mondeIG.*;
import twisk.exceptions.*;
import twisk.outils.*;
import twisk.vues.Observateur;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SimulationIG extends SujetObserve implements Observateur {

    private MondeIG mondeIG;
    private CorrespondanceEtapes correspondanceEtapes;
    private int nbClient;

    private Object instanceClassperso;
    private Monde monde;
    private GestionnaireClients gestionnaireClients;
    private int choixLoi;

    /**
     * Constructeur de SimulationIG
     * @param mondeIG monde que l'on va verifier et simuler
     */
    public SimulationIG(MondeIG mondeIG){
        this.mondeIG = mondeIG;
        this.ajouterObservateur(mondeIG);
        this.nbClient = 10;
        this.choixLoi = 1;
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
    public void stoperSimulation() {
        ThreadsManager.getInstance().destroyThreads();
        try {
            ClassLoaderPerso classLoader = new ClassLoaderPerso(this.mondeIG.getClass().getClassLoader());
            Class<?> classePerso = classLoader.loadClass("twisk.simulation.Simulation");
            Constructor<?> constructor = classePerso.getConstructor();
            this.mondeIG.tuerProcessus();
            notifierObservateur();
        } catch (Exception e) {
        }
    }


    /**
     * Verification de la validité du monde
     */
    private void verifierMonderIG() throws MondeInvalideException {
        if(this.mondeIG.getNbEtape() == 0){
            throw new MondeInvalideException("Erreur: il n'y a pas d'étapes");
        }
        if (!this.mondeIG.aEntree()) {
            throw new MondeInvalideException("Erreur: il n'y a pas d'entrée");
        }

        if (!this.mondeIG.aSortie()) {
            throw new MondeInvalideException("Erreur: il n'y a pas de sortie");
        }
        ArrayList<EtapeIG> entree = new ArrayList<>();
        for (EtapeIG etape : this.mondeIG) {
            if(etape.estUneEntree())
                entree.add(etape);
            ArrayList<EtapeIG> successeurs = etape.getSuccesseurs();
            if(etape.estUneSortie()){
                if(etape.getNbSuccesseurs() > 0){
                    throw new MondeInvalideException("Erreur: Une sortie ne peut avoir de successeurs");
                }
            }
            if(verifierChemin(etape)){
                throw new MondeInvalideException("Erreur: une/des étapes mènes à une impasse");
            }

            if (etape.estUnGuichet()) {
                if (etape.estUneSortie()) {
                    throw new MondeInvalideException("Erreur: un guichet ne peut être une sortie");
                }
                if (etape.getNbSuccesseurs() > 1) {
                    throw new MondeInvalideException("Erreur: un guichet ne peut avoir qu'un seul successeur");
                }
                if (successeurs.get(0).estUnGuichet()) {
                    throw new MondeInvalideException("Erreur: un guichet ne peut avoir comme successeur un autre guichet");
                }
                ((ActiviteIG) successeurs.get(0)).setEstRestreinte(true);
                if (successeurs.get(0).estUneEntree()) {
                    throw new MondeInvalideException("Erreur: une activité restreinte ne peut etre une entrée");
                }
                if (successeurs.get(0).getPredecesseurs().size() > 1) {
                    throw new MondeInvalideException("Erreur: une activité restreinte ne peut avoir qu'un seul predecesseur");
                }
            }
        }
        verifierNbEtape(entree);
    }

    /**
     * Méthode qui permet de vérifier que toutes les étapes sont atteignables
     * @param entrees la liste de toutes les entrées du monde
     * @throws MondeInvalideException l'exception du monde invalide
     */
    private void verifierNbEtape(ArrayList<EtapeIG> entrees) throws MondeInvalideException {
        Set<EtapeIG> visitees = new HashSet<>();

        for (EtapeIG entree : entrees) {
            dfs(entree, visitees);
        }

        if (visitees.size() != this.mondeIG.getNbEtape()) {
            throw new MondeInvalideException("Erreur: toutes les étapes du monde ne sont pas accessibles depuis une entrée.");
        }
    }

    /**
     * Parcours en profondeur (DFS) pour visiter toutes les étapes accessibles depuis une étape donnée
     * @param etape l'étape de départ
     * @param visitees ensemble des étapes visitées
     */
    private void dfs(EtapeIG etape, Set<EtapeIG> visitees) {
        if (!visitees.contains(etape)) {
            visitees.add(etape);
            for (EtapeIG successeur : etape.getSuccesseurs()) {
                dfs(successeur, visitees);
            }
        }
    }


    /**
     * Vérifie s'il y'a une sortie à chaque fin de chemin
     * Utilisation du DFS
     * @param etape l'étape de départ
     * @return false si le monde est juste, sinon true
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
        //FabriqueIdentifiant.getInstance().reset();
        FabriqueNumero.getInstance().resetNumeroEtape();
        FabriqueNumero.getInstance().resetNumeroSemaphore();
        Monde monde = new Monde();
        monde.setChoixLoi(this.getChoixLoi());
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
        } catch (InvocationTargetException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
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

    /**
     * Renvoie la loi définie
     * @return la loi définie
     */
    public int getChoixLoi() {
        return choixLoi;
    }

    /**
     * Défini la loi à utiliser
     * @param choixLoi la loi choisie
     */
    public void setChoixLoi(int choixLoi) {
        this.choixLoi = choixLoi;
    }

    @Override
    public void reagir() {
        this.gestionnaireClients = getGestionnaireClients();
        this.mondeIG.setGestionnaireClients(this.gestionnaireClients);
        this.mondeIG.setCorrespondanceEtapes(this.correspondanceEtapes);
        this.notifierObservateur();
    }
}
