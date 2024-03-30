package main.java.twisk.outils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class KitC {

    public KitC(){

    }

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

    public void creerFichier(String codeC){
        System.out.println(codeC);
        FileWriter flot;
        try{
            flot = new FileWriter("/tmp/twisk/client.c");
            flot.write(codeC);
            flot.close();
        }
        catch (IOException e){
            System.out.println("Erreur ecriture client.c");
        }
    }
    public void compiler(){
        ProcessBuilder pb = new ProcessBuilder("gcc", "-Wall", "-ansi",
                "-pedantic", "-fPIC", "-c", "/tmp/twisk/client.c", "-o",
                "/tmp/twisk/client.o");
        try {
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
    public void construireLaBibliotheque(){
        ProcessBuilder pb = new ProcessBuilder("gcc", "-shared", "/tmp/twisk/programmeC.o", "/tmp/twisk/codeNatif.o", "/tmp/twisk/client.o", "-o", "/tmp/twisk/libTwisk.so");
        try {
            pb.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
