package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import twisk.exceptions.DelaiEcartException;
import twisk.mondeIG.MondeIG;

import java.util.Optional;

public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;

    /**
     * Constructeur de la classe VueMenu
     * @param monde le MondeIG
     */
    public VueMenu(MondeIG monde) {
        super();
        this.monde = monde;

        Menu menuFichier = new Menu("Fichier");
        MenuItem quitter = new MenuItem("Quitter");
        quitter.setOnAction(event -> Platform.exit());
        quitter.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuFichier.getItems().add(quitter);

        Menu menuEdition = new Menu("Édition");
        MenuItem supprimer = new MenuItem("Supprimer");
        supprimer.setOnAction(new EcouteurSupprimer(monde));
        supprimer.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        MenuItem renommer = new MenuItem("Renommer");
        renommer.setOnAction(event -> this.renommerEtape());
        renommer.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));

        MenuItem effacer = new MenuItem("Effacer");
        effacer.setOnAction(event -> this.effacerSelection());
        effacer.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        menuEdition.getItems().addAll(supprimer, renommer, effacer);

        Menu menuMonde = new Menu("Monde");
        MenuItem entree = new MenuItem("Entrée");
        entree.setOnAction(event -> this.defEntree());
        MenuItem sortie = new MenuItem("Sortie");
        sortie.setOnAction(event -> this.defSortie());
        menuMonde.getItems().addAll(entree, sortie);

        Menu manuParametre = new Menu("Paramètre");
        MenuItem delai = new MenuItem("Délai");
        delai.setOnAction(event -> defDelai());
        MenuItem ecartTemps =  new MenuItem("Ecart-temps");
        ecartTemps.setOnAction(event -> defEcartTemps());
        manuParametre.getItems().addAll(delai,ecartTemps);

        // Ajout des menus à la barre de menu
        this.getMenus().addAll(menuFichier, menuEdition, menuMonde, manuParametre);

        this.monde.ajouterObservateur(this);
    }

    /**
     * Méthode qui permet de définir le nouvel écart-temps d'une étape,
     * ouvre une boite de dialogue pour prendre note de la durée,
     * si il n'y a qu'une seul étape de sélectionné
     */
    private void defEcartTemps() {
        if (this.monde.getNbEtapeSelectionner() == 1) {
            TextInputDialog text = new TextInputDialog();
            text.setTitle("Définir un écart de temps");
            text.setHeaderText(null);
            text.setContentText("Entrez la nouvelle valeur de l'écart de temps : ");
            Optional<String> result = text.showAndWait();

            // convertions en entier
            result.ifPresent(input -> {
                try {
                    int ecartTemps = Integer.parseInt(input);
                    this.monde.premiereEtapeSelectionnee().setEcartTemps(ecartTemps);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir un entier valide.");
                    alert.showAndWait();
                } catch (DelaiEcartException e) {
                    throw new RuntimeException(e);
                }
            });

            this.effacerSelection();
        }
    }


    /**
     * Méthode qui permet de définir le nouveau délai d'une étape,
     * ouvre une boite de dialogue pour prendre note de la durée,
     * si il n'y a qu'une seul étape de sélectionné
     */
    private void defDelai() {
        if (this.monde.getNbEtapeSelectionner() == 1) {
            TextInputDialog text = new TextInputDialog();
            text.setTitle("Définir un délais");
            text.setHeaderText(null);
            text.setContentText("Entrez la nouvelle valeur du délais : ");
            Optional<String> result = text.showAndWait();

            // convertions en entier
            result.ifPresent(input -> {
                try {
                    int delai = Integer.parseInt(input);
                    this.monde.premiereEtapeSelectionnee().setDelai(delai);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir un entier valide.");
                    alert.showAndWait();
                }
            });

            this.effacerSelection();
        }
    }

    /**
     * Méthode qui définie une étape comme une entrée,
     * si il n'y a qu'une seul étape de sélectionné
     */
    private void defEntree() {
        if(this.monde.getNbEtapeSelectionner() == 1) {
            if(this.monde.premiereEtapeSelectionnee().estUneEntree()){
                this.monde.premiereEtapeSelectionnee().SupprimerCommeEntree();
            } else {
                this.monde.premiereEtapeSelectionnee().DefinirCommeEntree();
            }
        }
        this.effacerSelection();
    }

    /**
     * Méthode qui définie une étape comme sortie,
     * si il n'y a qu'une seul étape de sélectionné
     */
    private void defSortie() {
        if(this.monde.getNbEtapeSelectionner() == 1) {
            if(this.monde.premiereEtapeSelectionnee().estUneSortie()){
                this.monde.premiereEtapeSelectionnee().SupprimerCommeSortie();
            } else {
                this.monde.premiereEtapeSelectionnee().DefinirCommeSortie();
            }
        }
        this.effacerSelection();
    }

    /**
     * Méthode qui déselctionne tout les arcs et toutes les étapes
     */
    private void effacerSelection() {
        this.monde.viderSelectionEtape();
        this.monde.viderSelectionArc();
        this.monde.notifierObservateur();
    }

    /**
     * Méthode qui permet de renommer un étape,
     * ouvre une boite de dialogue pour prendre note du nouveau nom,
     * si il n'y a qu'une seul étape de sélectionné
     */
    private void renommerEtape() {
        if(this.monde.getNbEtapeSelectionner() == 1) {
            TextInputDialog text = new TextInputDialog();
            text.setTitle("Renommer une étape");
            text.setHeaderText(null);
            text.setContentText("Nouveau nom : ");
            Optional<String> result = text.showAndWait();

            result.ifPresent(s -> this.monde.renommer(s));
            this.effacerSelection();
        }
    }

    @Override
    public void reagir() {
        // Réaction aux changements dans le monde
    }
}

