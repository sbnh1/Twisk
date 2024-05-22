package twisk;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.vues.VueMenu;
import twisk.vues.VueOutils;
import twisk.vues.VueMondeIG;
import javafx.scene.Scene;


public class MainTwisk extends Application {

    @Override
    public void start(Stage primaryStage) {
        MondeIG monde = new MondeIG();
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Twisk");
        root.setBottom(new VueOutils(monde));
        root.setCenter(new VueMondeIG(monde));
        root.setTop(new VueMenu(monde));

        primaryStage.setScene(new Scene(root, TailleComposants.getInstance().largeur, TailleComposants.getInstance().hauteur));
        monde.creerMondeDeBase(monde);
        monde.notifierObservateur();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/*
ligne d'éxecution pour moi :
java -jar --module-path /home/houssein/cours/interfaceGraphique/javafx_17/lib --add-modules javafx.controls -ea twiskIG.jar
*/
