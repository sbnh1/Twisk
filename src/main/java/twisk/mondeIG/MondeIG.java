package twisk.mondeIG;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import twisk.exceptions.PointDeControleException;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.TailleComposants;
import twisk.simulation.GestionnaireClients;
import twisk.simulation.Simulation;
import twisk.vues.Observateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG>, Observateur {
    private HashMap<String, EtapeIG> etapes;

    @Expose(serialize = false, deserialize = false)
    private transient ArrayList<EtapeIG> etapesSelectionnees;

    private ArrayList<ArcIG> arcs;

    @Expose(serialize = false, deserialize = false)
    private transient PointDeControleIG premierPointDeControle;

    @Expose(serialize = false, deserialize = false)
    private transient ArrayList<ArcIG> arcsSelectionnes;

    @Expose(serialize = false, deserialize = false)
    private transient GestionnaireClients gestionnaireClients;

    @Expose(serialize = false, deserialize = false)
    private transient CorrespondanceEtapes corres;

    /**
     * Constructeur de la classe MondeIG
     */
    public MondeIG(){
        this.etapes = new HashMap<>();
        this.arcs = new ArrayList<>();
        this.etapesSelectionnees = new ArrayList<>();
        this.arcsSelectionnes = new ArrayList<>();
    }

    public void reset(){
        this.etapes = new HashMap<>();
        this.arcs = new ArrayList<>();
        this.etapesSelectionnees = new ArrayList<>();
        this.arcsSelectionnes = new ArrayList<>();
    }

    /**
     * Créer une nouvelle activité
     * lui donnant un identifiant unique
     * @param type le type de l'étape en String
     */
    public void ajouter(String type){
        if(type.equals("activite")){
            EtapeIG etapeAjoute = new ActiviteIG(type, TailleComposants.getInstance().activiteLargeur, TailleComposants.getInstance().activiteHauter);
            String id = etapeAjoute.getIdentifiant();
            etapeAjoute.setNom(id);
            this.etapes.put(id, etapeAjoute);
        } else {
            EtapeIG guichetAjoute = new GuichetIG(type, TailleComposants.getInstance().guichetLargeur, TailleComposants.getInstance().guichetHauteur);
            String id = guichetAjoute.getIdentifiant();
            guichetAjoute.setNom(id);
            this.etapes.put(id, guichetAjoute);
        }
    }

    public void ajouter(EtapeIG etape){
        this.etapes.put(etape.getIdentifiant(), etape);
    }

    /**
     * renvoie vrai si le monde a bien des une ou plusieurs entrees
     */
    public boolean aEntree(){
        for(EtapeIG etape : this){
            if(etape.estUneEntree()){
                return true;
            }
        }
        return false;
    }

    /**
     * renvoie vrai si le monde a bien des une ou plusieurs sorties
     */
    public boolean aSortie(){
        for(EtapeIG etape: this){
            if(etape.estUneSortie()){
                return true;
            }
        }
        return false;
    }

    /**
     * Récupère le nombre d'étape dans le MondeIG
     * @return le nombre d'étape dans le MondeIG
     */
    public int getNbEtape(){
        return this.etapes.size();
    }

    /**
     * Récupère le nombre d'arc dans le MondeIG
     * @return le nombre d'arc dans le MondeIG
     */
    public int getNbArc(){
        return this.arcs.size();
    }

    /**
     * Retourne un itérateur sur la liste des EtapeIG
     * @return un itérateur sur la liste des EtapeIG
     */
    @Override
    public Iterator<EtapeIG> iterator() {
        return etapes.values().iterator();
    }

    /**
     * Retourne un itérateur sur la liste des ArcIG
     * @return un itérateur sur la liste des ArcIG
     */
    public Iterator<ArcIG> arcIterator() { return arcs.iterator(); }

    /**
     * Créer un arc qui respect les contraintes du mondeIG à partir de deux points de controle
     * @param pt1 point de controle de départ de l'arc
     * @param pt2 point de controle d'arrivé de l'arc
     * @throws PointDeControleException si les deux points ne respecte pas une des contraintes imposé par le MondeIG
     */
    public void ajouter(PointDeControleIG pt1, PointDeControleIG pt2) throws PointDeControleException {
        EtapeIG etape1 = pt1.getEtapeIG();
        EtapeIG etape2 = pt2.getEtapeIG();

        try {
            if (pt1.equals(pt2)) {
                throw new PointDeControleException("Erreur : Les points de contrôle doivent être différents.");
            }
            for (ArcIG arc : arcs) {
                if (arc.getPointDeControleDepart().equals(pt1) && arc.getPointDeControleArrivee().equals(pt2)) {
                    throw new PointDeControleException("Erreur : Un arc existe déjà entre ces points de contrôle.");
                }
            }
            if (pt1.getEtapeIG() == pt2.getEtapeIG()) {
                throw new PointDeControleException("Erreur : Les points de contrôle appartiennent à la même étape.");
            }

            ArcIG arcIG = new ArcIG(pt1, pt2);
            this.arcs.add(arcIG);
            etape1.ajouterSuccesseur(etape2);
            etape2.ajouterPredecesseur(etape1);

            if(verifierCycle(etape1, etape2)){
                this.supprimerArc(arcIG);
                throw new PointDeControleException("Erreur : Les cycles ne sont pas admis");
            }
            if(etape2.estUnGuichet()){
                pt2.setSensCirculation(true);
            }
        } catch (PointDeControleException e) {
        }
    }

    /**
     * Vérification si le monde est acyclique, en utilisant un DFS
     * @param candidat l'étape recherchée
     * @param racine l'étape depuis la quel l'étape candidat est recherché
     * @return false si acyclique, sinon true
     */
    private boolean verifierCycle(EtapeIG candidat, EtapeIG racine) {
        // Initialisation de l'ensemble des étapes visitées
        Set<EtapeIG> visitees = new HashSet<>();
        Stack<EtapeIG> pile = new Stack<>();

        pile.push(racine);

        while (!pile.isEmpty()) {
            EtapeIG current = pile.pop();
            // Si on trouve le candidat, il y a un cycle
            if (current.equals(candidat)) {
                return true;
            }
            // Ajouter l'étape courante à l'ensemble des visités
            if (!visitees.contains(current)) {
                visitees.add(current);
                // Ajouter tous les successeurs non visités à la pile pour le parcours en profondeur
                for (EtapeIG successeur : current.getSuccesseurs()) {
                    if (!visitees.contains(successeur)) {
                        pile.push(successeur);
                    }
                }
            }
        }

        // Si on a parcouru tout le graphe sans trouver le candidat, il n'y a pas de cycle
        return false;
    }


    /**
     * Sélectionne un point de controle, appelle la fonction ajoutée avec en parametre,
     * le point de controle précédemment sélectionné et l'actuel.
     * S'il n'y a pas de point de controle précédent, il le devient,
     * sinon les deux points de controle sont oublié après l'apelle de la fonction ajoutée
     * @param pointDeControleIG point de controle sélectionné
     * @throws PointDeControleException si une erreur est trouvé dans la méthode ajouter (PointDeControleIG pt1, PointDeControleIG pt2)
     */
    public void pointDeControleSelectionne(PointDeControleIG pointDeControleIG) throws PointDeControleException {
        if(premierPointDeControle == null){
            premierPointDeControle = pointDeControleIG;
            premierPointDeControle.setEstSelectionnee(true);
        } else {
            ajouter(premierPointDeControle, pointDeControleIG);
            premierPointDeControle.setEstSelectionnee(false);
            premierPointDeControle = null;
            notifierObservateur();
        }
    }

    /**
     * Retourne la liste des ArcIG
     * @return la liste des ArcIG
     */
    public List<ArcIG> getArcs() {
        return arcs;
    }

    /**
     * Supprime tout les arcs donné en paramètre
     * @param arcsASupprimer liste des arcs qui vont être supprimé
     */
    public void supprimerArc(ArcIG... arcsASupprimer) {
        for (ArcIG arc : arcsASupprimer) {
            if (arcs.contains(arc)) {
                arcs.remove(arc);
            }
            arc.getPointDeControleDepart().getEtapeIG().supprimerSuccessseur(arc.getPointDeControleArrivee().getEtapeIG());
            arc.getPointDeControleArrivee().getEtapeIG().supprimerPredecesseur(arc.getPointDeControleDepart().getEtapeIG());
        }
        notifierObservateur();
    }


    /**
     * Retourne une forme textuelle de toute les étapes du MondeIG
     * @return une forme textuelle de toute les étapes du MondeIG
     */
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder("Monde :\n");
        for(EtapeIG etape : this){
            res.append(etape.getNom()).append(" ").append(etape.getIdentifiant()).append("\n");
        }
        return res.toString();
    }

    /**
     * Supprime toutes les étapes donné en paramètre
     * @param etapes liste des étapes qui vont être supprimé
     */
    public void supprimerEtape(EtapeIG... etapes) {
        for (EtapeIG etape : etapes) {
            this.etapes.remove(etape.getIdentifiant());
            deselectionnerEtape(etape);
            if (premierPointDeControle != null) {
                if (premierPointDeControle.getEtapeIG() == etape) {
                    premierPointDeControle = null;
                }
            }
        }
        notifierObservateur();
    }

    /**
     * Ajoute l'étape dans la liste des étapes sélectionné
     * @param etape l'étape qui est sélectionné
     */
    public void selectioneEtape(EtapeIG etape) {
        etapesSelectionnees.add(etape);
    }

    /**
     * Retire l'étape de la liste des étapes sélectionné
     * @param etape l'étape qui n'est plus sélectionné
     */
    public void deselectionnerEtape(EtapeIG etape) {
        etapesSelectionnees.remove(etape);
    }

    /**
     * Retourne un booléen pour dire si une étape est dans la liste des étapes sélectionné
     * @param etape l'étape questionné
     * @return vrai si l'étape est sélectionné, false sinon
     */
    public boolean estSelectionneeEtape(EtapeIG etape) {
        return etapesSelectionnees.contains(etape);
    }

    /**
     * Méthode qui permet d'obtenir facilement la premiere étape séléctionnée
     * à utiliser quand on a vérifié qu'il n'y a qu'une étape de sélectionnée
     * @return la premiere étape qui a été selectionnée
     */
    public EtapeIG premiereEtapeSelectionnee() {
        if (!etapesSelectionnees.isEmpty()) {
            return etapesSelectionnees.get(0);
        } else {
            return null; // Aucune étape sélectionnée
        }
    }

    /**
     * Méthode qui déselectionne toute les étapes sélectionné
     */
    public void viderSelectionEtape() {
        etapesSelectionnees.clear();
    }

    /**
     * Retourne la liste des EtapeIG qui sont sélectionné
     * @return la liste des EtapeIG qui sont sélectionné
     */
    public List<EtapeIG> getEtapesSelectionnees() {
        return etapesSelectionnees;
    }

    /**
     * Retourne la taille de la liste d'étape sélectionné
     * @return le nombre d'étape sélectionné
     */
    public int getNbEtapeSelectionner() {
        return etapesSelectionnees.size();
    }

    /**
     * Renomme la premiere étape sélectionné par un nonuveau nom
     * faire attention qu'il n'y a qu'une seul activité sélectionné avant de l'utiliser
     * @param nom nouveau nom de l'étape
     */
    public void renommer(String nom) {
        premiereEtapeSelectionnee().setNom(nom);
    }

    /**
     * Ajoute l'arc dans la liste des étapes sélectionné
     * @param arc l'arc qui est sélectionné
     */
    public void selectionnerArc(ArcIG arc) {
        arcsSelectionnes.add(arc);
    }

    /**
     * Retire l'arc de la liste des étapes sélectionné
     * @param arc l'arc qui n'est plus sélectionné
     */
    public void deselectionnerArc(ArcIG arc) {
        arcsSelectionnes.remove(arc);
    }

    /**
     * Retourne un booléen pour dire si un arc est dans la liste des arc sélectionné
     * @param arc l'arc questionné
     * @return vrai si l'arc est sélectionné, false sinon
     */
    public boolean estSelectionneArc(ArcIG arc) {
        return arcsSelectionnes.contains(arc);
    }

    /**
     * Méthode qui déselectionne tout les arcs sélectionné
     */
    public void viderSelectionArc() {
        arcsSelectionnes.clear();
    }

    /**
     * Retourne la liste des ArcIG qui sont sélectionné
     * @return la liste des ArcIG qui sont sélectionné
     */
    public List<ArcIG> getArcsSelectionnes() {
        return arcsSelectionnes;
    }

    /**
     * Supprime tout les arcs sélectionné
     */
    public void supprimerArcSelectionne() {
        for (ArcIG arc : arcsSelectionnes) {
            arc.getPointDeControleDepart().setSensCirculation(false);
            arc.getPointDeControleArrivee().setSensCirculation(false);
            arcs.remove(arc);
            arc.getPointDeControleDepart().getEtapeIG().supprimerSuccessseur(arc.getPointDeControleArrivee().getEtapeIG());
            arc.getPointDeControleArrivee().getEtapeIG().supprimerPredecesseur(arc.getPointDeControleDepart().getEtapeIG());
        }
        viderSelectionArc();
        notifierObservateur();
    }

    /**
     * Methode qui modifie la position d'une EtapeIG
     * @param id L'identifiant de l'EtapeIG
     * @param posX La position X de l'EtapeIG
     * @param posY La position Y de l'EtapeIG
     */
    public void setPosEtapeIG(String id, int posX, int posY){

        EtapeIG etapeIG = this.etapes.get(id);
        etapeIG.setPosX(posX);
        etapeIG.setPosY(posY);
        this.notifierObservateur();
    }
    /**
     * Methode qui positionne les PointDeControleIG d'une EtapeIG
     * @param id L'identifiant de l'EtapIG
     */
    public void setPosPtEtapeIG(String id){
        EtapeIG etapeIG = this.etapes.get(id);
        etapeIG.setPosPtCtrl();
        this.notifierObservateur();
    }


    private void clearSuccesseurs(){
        for(EtapeIG etape : this){
            etape.clearSuccesseurs();
        }
    }

    /**
     * Renvoie le gestionnaire de clients
     * @return le gestionnaire de clients
     */
    public GestionnaireClients getGestionnaireClients(){
        return this.gestionnaireClients;
    }

    /**
     * Méthode qui créer une activité et un guichet au lancement de la simulation
     * @param monde le monde
     */
    public void creerMondeDeBase(MondeIG monde){
        monde.ajouter("activite");
        monde.ajouter("guichet");
    }


    /**
     * Modifie le gestionnaire de clients avec celui donné en paramètre
     * @param gestionnaireClients le nouveau gestionnaire de client
     */
    public void setGestionnaireClients(GestionnaireClients gestionnaireClients) {
        this.gestionnaireClients = gestionnaireClients;
    }

    /**
     * Modifie la correspondance étapes avec celle donnée en paramètre
     * @param correspondanceEtapes la nouvelle correspondance étapes
     */
    public void setCorrespondanceEtapes(CorrespondanceEtapes correspondanceEtapes){
        this.corres = correspondanceEtapes;
    }

    /**
     * Methode qui renvoie la CorrespondanceEtapes du MondeIG
     * @return La CorrespondanceEtapes
     */
    public CorrespondanceEtapes getCorrespondanceEtapes(){
        return this.corres;
    }

    @Override
    public void reagir() {
        Platform.runLater(this::notifierObservateur);
        //mettre a jour vumondeig quand il sera dans le bon thread (platform.runnable)
    }

    public String toJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Map<String, String>> arcs = new ArrayList<>();
        for(ArcIG arc : this.arcs){
            Map<String, String> newArc = new HashMap<>();
            newArc.put("depart", arc.getPointDeControleDepart().getIdentifiant());
            newArc.put("arrivee", arc.getPointDeControleArrivee().getIdentifiant());
            arcs.add(newArc);
        }

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("etapes", this.etapes);
        jsonData.put("arcs", arcs);

        return gson.toJson(jsonData);
    }
}
