package twisk.outils;

import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class KitC {


    /**
     * Constructeur sans argument de la classe KitC
     */
    public KitC(){
    }

    /**
     * Méthode qui permet de créer l'environnement de gestion des fichiers C
     * la méthode créer des fichiers temporaires sous le repertoire /tmp
     * @throws IOException si une erreur survient lors de la création du répertoire ou de la copie des fichiers
     */
    public void creerEnvironnement(){
        Path directory = Paths.get("/tmp/twisk");
        try {
            Files.createDirectories(directory);
            String[] liste = {"programmeC.o", "def.h", "codeNatif.o"};
            for (String nom : liste) {
                InputStream src = getClass().getResourceAsStream("/codeC/" + nom);
                Path dest = directory.resolve(nom);
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui écrit le code donné en parametre en fichier C qui sera utilisé dans la simulation
     * @param codeC le codeC du monde écrit grace au méthode toC()
     * @throws IOException si une erreur survient lors de l'éxecution du processus
     */
    public void creerFichier(String codeC){
        System.out.println(codeC);
        FileWriter flot;
        try{
            flot = new FileWriter("/tmp/twisk/client.c");
            flot.write(codeC);
            flot.close();
        }
        catch (IOException e){
        }
    }

    /**
     * Méthode qui compile tout le fichier C créer grace à la méthode creerFichier(String code)
     * @throws InterruptedException si le processus est interrompu pendant l'éxecution
     * @throws IOException si une erreur survient lors de l'éxecution du processus
     */
    public void compiler(){
        ProcessBuilder pb = new ProcessBuilder("gcc", "-Wall", "-std=gnu99",
                "-pedantic", "-fPIC", "-c", "/tmp/twisk/client.c", "-o",
                "/tmp/twisk/client.o");
        try {
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui construit la bibliothèque utilisé
     * @throws InterruptedException si le processus est interrompu pendant l'éxecution
     * @throws IOException si une erreur survient lors de l'éxecution du processus
     */
    public void construireLaBibliotheque(){
        ProcessBuilder pb = new ProcessBuilder("gcc", "-shared", "/tmp/twisk/programmeC.o", "/tmp/twisk/codeNatif.o", "/tmp/twisk/client.o", "-o", "/tmp/twisk/libTwisk" + FabriqueIdlib.getInstance().getId() + ".so");
        try {
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui KILL tous les clients
     * @param gestionnaireClients le gestionnaire où sont les clients à TUER
     * @throws IOException l'exception du siècle
     */
    public void tuerProcessus(GestionnaireClients gestionnaireClients) throws IOException{
        if(gestionnaireClients != null) {
            for (Client c : gestionnaireClients) {
                Runtime.getRuntime().exec("kill -9 " + c.getNumeroClient());
            }
        }
    }
}
