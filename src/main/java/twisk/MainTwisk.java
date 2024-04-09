package main.java.twisk;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.java.twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import main.java.twisk.vues.VueMenu;
import main.java.twisk.vues.VueOutils;
import main.java.twisk.vues.VueMondeIG;
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
