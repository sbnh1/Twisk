package twisk.mondeIG;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import twisk.exceptions.PointDeControleException;
import twisk.monde.Guichet;
import twisk.outils.TailleComposants;

import java.util.*;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG> {
    private HashMap<String, EtapeIG> etapes;
    private ArrayList<EtapeIG> etapesSelectionnees;
    private ArrayList<ArcIG> arcs;
    private PointDeControleIG premierPointDeControle;
    private ArrayList<ArcIG> arcsSelectionnes;

    /**
     * Constructeur de la classe MondeIG
     */
    public MondeIG(){
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
            EtapeIG etapeAjoute = new ActiviteIG(type, TailleComposants.getInstance().activiteLargeur, TailleComposants.getInstance().activiteHauteur);
            String id = etapeAjoute.getIdentifiant();
            this.etapes.put(id, etapeAjoute);
        } else {
            EtapeIG guichetAjoute = new GuichetIG(type, TailleComposants.getInstance().activiteLargeur, TailleComposants.getInstance().activiteHauteur);
            String id = guichetAjoute.getIdentifiant();
            this.etapes.put(id, guichetAjoute);
        }
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
                return false;
            }
        }
        return true;
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
            etape1.ajouter(etape2);
        } catch (PointDeControleException e) {
            afficherAlerte(e.getMessage());
        }
    }

    /**
     * Sélectionne un point de controle, appelle la fonction ajouter avec en parametre,
     * le point de controle précédemment sélectionné et l'actuel.
     * Si il n'y a pas de point de controle précédent il le devient,
     * sinon les deux points de controle sont oublié après l'apelle de la fonction ajouté
     * @param pointDeControleIG point de controle sélectionné
     * @throws PointDeControleException si une erreur est trouvé dans la méthode ajouter(PointDeControleIG pt1, PointDeControleIG pt2)
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
            arcs.remove(arc);
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

    /**
     * Affiche une alerte à l'utilisateur
     * puis la ferme automatiquement après 3 secondes
     * @param message le message affiché
     */
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        PauseTransition pause = new PauseTransition(Duration.millis(3000));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

}
