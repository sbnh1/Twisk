package twisk.vues;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twisk.exceptions.DelaiEcartException;
import twisk.exceptions.PointDeControleException;
import twisk.mondeIG.*;
import twisk.outils.FabriqueIdentifiant;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;
    private Stage primaryStage;

    /**
     * Constructeur de la classe VueMenu
     * @param monde le MondeIG
     */
    public VueMenu(MondeIG monde, Stage primaryStage) {
        super();
        this.monde = monde;
        this.primaryStage = primaryStage;

        Menu menuFichier = new Menu("Fichier");
        MenuItem quitter = new MenuItem("Quitter");
        MenuItem exporter = new MenuItem("exporter");
        MenuItem importer = new MenuItem("importer");
        exporter.setOnAction(event -> this.exporter());
        importer.setOnAction(event -> this.importer());
        quitter.setOnAction(event -> {
            Platform.exit();
            this.monde.tuerProcessus();
        });
        quitter.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        menuFichier.getItems().add(quitter);
        menuFichier.getItems().add(exporter);
        menuFichier.getItems().add(importer);

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
        entree.setAccelerator((KeyCombination.keyCombination("Ctrl+I")));
        MenuItem sortie = new MenuItem("Sortie");
        sortie.setOnAction(event -> this.defSortie());
        sortie.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        MenuItem entreeSortie = new MenuItem("Entrée et Sortie");
        entreeSortie.setOnAction(event -> this.defSortieEtEntree());
        entreeSortie.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        menuMonde.getItems().addAll(entree, sortie, entreeSortie);

        Menu menuParametre = new Menu("Paramètre");
        MenuItem delai = new MenuItem("Délai");
        delai.setOnAction(event -> defDelai());
        MenuItem ecartTemps =  new MenuItem("Ecart-temps");
        ecartTemps.setOnAction(event -> defEcartTemps());
        MenuItem jetons = new MenuItem("Jetons");
        jetons.setOnAction(event -> defNombreDeJetons());
        menuParametre.getItems().addAll(delai,ecartTemps,jetons);

        Menu menuTwist = new Menu("Monde");
        MenuItem reset = new MenuItem("supprimer Monde");
        reset.setOnAction(event -> reset());
        MenuItem monde1 = new MenuItem("Monde 1");
        monde1.setOnAction(event -> importerMonde("/import/monde1.json"));
        MenuItem monde2 = new MenuItem("Monde 2");
        monde2.setOnAction(event -> importerMonde("/import/monde2.json"));
        MenuItem monde3 = new MenuItem("Monde 3");
        monde3.setOnAction(event -> importerMonde("/import/monde3.json"));
        MenuItem monde4 = new MenuItem("Monde 4");
        monde4.setOnAction(event -> importerMonde("/import/monde4.json"));
        menuTwist.getItems().addAll(reset, monde1, monde2, monde3, monde4);

        // Ajout des menus à la barre de menu
        this.getMenus().addAll(menuFichier, menuEdition, menuMonde, menuParametre, menuTwist);
        this.setStyle("-fx-background-color: #e5e5dc;");

        this.monde.ajouterObservateur(this);
    }


    /**
     * Méthode qui créer un monde, depuis un monde sous Json déjà existant
     * @param path le chemin vers le monde depuis le repertoire resources root
     */
    private void importerMonde(String path) {
        this.monde.reset();

        FabriqueIdentifiant.getInstance().reset();

        Gson gson = new Gson();


        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier data.json n'a pas été trouvé.");
            alert.showAndWait();
            return;
        }

        InputStreamReader reader = new InputStreamReader(inputStream);

        Map<String, Object> data = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
        Map<String, Object> etapes = (Map<String, Object>) data.get("etapes");
        List<Map<String, Object>> arcs = (List<Map<String, Object>>) data.get("arcs");

        Map<String, PointDeControleIG> points = new HashMap<>();



        for (Map.Entry<String, Object> entry : etapes.entrySet()) {
            Map<String, Object> etape = (Map<String, Object>) entry.getValue();
            if((boolean)etape.get("estUneActivite")){
                ActiviteIG activite = new ActiviteIG(
                        (String)etape.get("nom"),
                        ((Double)etape.get("largeur")).intValue(),
                        ((Double)etape.get("hauteur")).intValue(),
                        (String)etape.get("identifiant")
                );
                activite.setPosX(
                        ((Double) etape.get("posX")).intValue()
                );
                activite.setPosY(
                        ((Double) etape.get("posY")).intValue()
                );
                activite.setPosPtCtrl();
                activite.setDelai(((Double)etape.get("delai")).intValue());
                if((boolean)etape.get("estUneEntree")){
                    activite.DefinirCommeEntree();
                }else if((boolean)etape.get("estUneSortie")){
                    activite.DefinirCommeSortie();
                }
                try {
                    activite.setEcartTemps(((Double) etape.get("ecartTemps")).intValue());
                } catch (DelaiEcartException e) {}


                for(PointDeControleIG point : activite.getPointsDeControle()){
                    points.put(point.getIdentifiant(), point);
                }
                this.monde.ajouter(activite);
            }else{
                GuichetIG guichet = new GuichetIG(
                        (String)etape.get("nom"),
                        ((Double) etape.get("largeur")).intValue(),
                        ((Double) etape.get("hauteur")).intValue(),
                        (String)etape.get("identifiant")
                );
                guichet.setPosX(
                        ((Double) etape.get("posX")).intValue()
                );
                guichet.setPosY(
                        ((Double) etape.get("posY")).intValue()
                );
                guichet.setPosPtCtrl();
                guichet.setNbJetons(((Double)etape.get("nbJetons")).intValue());
                if((boolean)etape.get("estUneEntree")){
                    guichet.DefinirCommeEntree();
                }else if((boolean)etape.get("estUneSortie")){
                    guichet.DefinirCommeSortie();
                }
                for(PointDeControleIG point : guichet.getPointsDeControle()){
                    points.put(point.getIdentifiant(), point);
                }
                this.monde.ajouter(guichet);
            }
        }

        for (Map.Entry<String, PointDeControleIG> entry : points.entrySet()) {
            String key = entry.getKey();
            PointDeControleIG value = entry.getValue();
        }

        for (Map<String, Object> arc : arcs) {
            try{
                this.monde.ajouter(
                        points.get((String)arc.get("depart")),
                        points.get((String)arc.get("arrivee"))
                );
            }catch(PointDeControleException e){

            }
        }
        FabriqueIdentifiant.getInstance().setNoEtape(((Double) data.get("fabriqueIdentifiant")).intValue());
        this.monde.notifierObservateur();
    }


    private void reset(){
        this.monde.reset();
        this.monde.notifierObservateur();
    }

    /**
     * Méthode qui permet de définir le nouveau nombre de jetons d'un guichet,
     * ouvre une boite de dialogue pour prendre note du nombre de jetons,
     * si il n'y a qu'une seul étape de sélectionné et que ce soit un guichet
     */
    private void defNombreDeJetons() {
        if(this.monde.getNbEtapeSelectionner() == 1 && !this.monde.premiereEtapeSelectionnee().estUneActivite()){
            TextInputDialog text = new TextInputDialog();
            text.setTitle("Définir le nombre de jetons");
            text.setHeaderText(null);
            text.setContentText("Entrez le nouveau nombre de jetons : ");
            Optional<String> result = text.showAndWait();

            result.ifPresent(input -> {
                try {
                    int jetons = Integer.parseInt(input);
                    if(jetons < 1){
                        throw new DelaiEcartException("Erreur : un guichet ne peux avoir que un nombre de jetons supérieur à 0");
                    }
                    this.monde.premiereEtapeSelectionnee().setNbJetons(jetons);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir un entier valide.");
                    alert.showAndWait();
                } catch (DelaiEcartException e) {
                }
            });
            this.effacerSelection();
        }
    }

    /**
     * Méthode qui permet de définir le nouvel écart-temps d'une activité,
     * ouvre une boite de dialogue pour prendre note de la durée,
     * si il n'y a qu'une seul étape de sélectionné
     */
    private void defEcartTemps() {
        if (this.monde.getNbEtapeSelectionner() == 1 && this.monde.premiereEtapeSelectionnee().estUneActivite()) {
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
        if (this.monde.getNbEtapeSelectionner() == 1 && this.monde.premiereEtapeSelectionnee().estUneActivite()) {
            TextInputDialog text = new TextInputDialog();
            text.setTitle("Définir un délais");
            text.setHeaderText(null);
            text.setContentText("Entrez la nouvelle valeur du délais : ");
            Optional<String> result = text.showAndWait();

            // convertions en entier
            result.ifPresent(input -> {
                try {
                    int delai = Integer.parseInt(input);
                    if(delai <= this.monde.premiereEtapeSelectionnee().getEcartTemps()){
                        throw new DelaiEcartException("Erreur : Le delais ne peut pas être plus petit que l'écart-temps");
                    } else {
                     this.monde.premiereEtapeSelectionnee().setDelai(delai);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir un entier valide.");
                    alert.showAndWait();
                } catch (DelaiEcartException e) {
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

    private void defSortieEtEntree(){
        if(this.monde.getNbEtapeSelectionner() == 1){
            if(!this.monde.premiereEtapeSelectionnee().estUneSortie()){
                this.monde.premiereEtapeSelectionnee().DefinirCommeSortie();
            }

            if(!this.monde.premiereEtapeSelectionnee().estUneEntree()){
                this.monde.premiereEtapeSelectionnee().DefinirCommeEntree();
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

    /**
     * Exporte le mondeIG en fichier Json
     */
    public void exporter(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers Json (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(this.primaryStage);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(this.monde.toJson());
            } catch (IOException e) {
            }
        }
    }

    /**
     * Importe le fichier Json en mondeIG
     */
    public void importer(){
        this.monde.reset();

        FabriqueIdentifiant.getInstance().reset();

        Gson gson = new Gson();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers Json (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(this.primaryStage);

        FileReader reader = null;

        try {
            reader = new FileReader(file);
        }catch (FileNotFoundException e){}


        Map<String, Object> data = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
        Map<String, Object> etapes = (Map<String, Object>) data.get("etapes");
        List<Map<String, Object>> arcs = (List<Map<String, Object>>) data.get("arcs");

        Map<String, PointDeControleIG> points = new HashMap<>();



        for (Map.Entry<String, Object> entry : etapes.entrySet()) {
            Map<String, Object> etape = (Map<String, Object>) entry.getValue();
            if((boolean)etape.get("estUneActivite")){
                ActiviteIG activite = new ActiviteIG(
                        (String)etape.get("nom"),
                        ((Double)etape.get("largeur")).intValue(),
                        ((Double)etape.get("hauteur")).intValue(),
                        (String)etape.get("identifiant")
                );
                activite.setPosX(
                        ((Double) etape.get("posX")).intValue()
                );
                activite.setPosY(
                        ((Double) etape.get("posY")).intValue()
                );
                activite.setPosPtCtrl();
                activite.setDelai(((Double)etape.get("delai")).intValue());
                if((boolean)etape.get("estUneEntree")){
                    activite.DefinirCommeEntree();
                }else if((boolean)etape.get("estUneSortie")){
                    activite.DefinirCommeSortie();
                }
                try {
                    activite.setEcartTemps(((Double) etape.get("ecartTemps")).intValue());
                } catch (DelaiEcartException e) {}


                for(PointDeControleIG point : activite.getPointsDeControle()){
                    points.put(point.getIdentifiant(), point);
                }
                this.monde.ajouter(activite);
            }else{
                GuichetIG guichet = new GuichetIG(
                        (String)etape.get("nom"),
                        ((Double) etape.get("largeur")).intValue(),
                        ((Double) etape.get("hauteur")).intValue(),
                        (String)etape.get("identifiant")
                );
                guichet.setPosX(
                        ((Double) etape.get("posX")).intValue()
                );
                guichet.setPosY(
                        ((Double) etape.get("posY")).intValue()
                );
                guichet.setPosPtCtrl();
                guichet.setNbJetons(((Double)etape.get("nbJetons")).intValue());
                if((boolean)etape.get("estUneEntree")){
                    guichet.DefinirCommeEntree();
                }else if((boolean)etape.get("estUneSortie")){
                    guichet.DefinirCommeSortie();
                }
                for(PointDeControleIG point : guichet.getPointsDeControle()){
                    points.put(point.getIdentifiant(), point);
                }
                this.monde.ajouter(guichet);
            }
        }

        for (Map.Entry<String, PointDeControleIG> entry : points.entrySet()) {
            String key = entry.getKey();
            PointDeControleIG value = entry.getValue();
        }

        for (Map<String, Object> arc : arcs) {
            try{
                this.monde.ajouter(
                        points.get((String)arc.get("depart")),
                        points.get((String)arc.get("arrivee"))
                );
            }catch(PointDeControleException e){

            }
        }
        FabriqueIdentifiant.getInstance().setNoEtape(((Double) data.get("fabriqueIdentifiant")).intValue());
        this.monde.notifierObservateur();
    }

    @Override
    public void reagir() {
        // Réaction aux changements dans le monde
    }

}