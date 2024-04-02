package test.java.twisk.outils;

import main.java.twisk.outils.KitC;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testKitC {
    @Test
    public void testCreerEnvironnement(){
        KitC kitC = new KitC();
        kitC.creerEnvironnement();
        File file = new File("/tmp/twisk");
        assertTrue(file.exists());
    }

    @Test
    public void testCreerEnvironnement2(){
        KitC kitC = new KitC();
        kitC.creerEnvironnement();
        File file = new File("/tmp/twisk");
        assertTrue(file.isDirectory());
    }
    @Test
    public void testCreerEnvironnement3(){
        KitC kitC = new KitC();
        kitC.creerEnvironnement();
        Path directory = Paths.get("/tmp/twisk");
        assertTrue(Files.exists(directory.resolve("programmeC.o")));
    }
    @Test
    public void testCreerEnvironnement4(){
        KitC kitC = new KitC();
        kitC.creerEnvironnement();
        Path directory = Paths.get("/tmp/twisk");
        assertTrue(Files.exists(directory.resolve("def.h")));
    }
    @Test
    public void testCreerBibliotheque(){
        KitC kitC = new KitC();
        kitC.creerEnvironnement();
        kitC.creerFichier("#include <stdio.h>\n" +
                "\n" +
                "int main() {\n" +
                "    printf(\"Hello, world!\\n\");\n" +
                "    return 0;\n" +
                "}");
        kitC.compiler();
        kitC.construireLaBibliotheque();
        Path directory = Paths.get("/tmp/twisk");
        assertTrue(Files.exists(directory.resolve("libTwisk.so")));
    }

    @Test
    public void testCreerFichier(){
        KitC kitC = new KitC();
        kitC.creerFichier("test");
        File file = new File("/tmp/twisk/client.c");
        assertTrue(file.exists());
    }
    @Test
    public void testCreerFichier2() {
        KitC kitC = new KitC();
        kitC.creerFichier("test");
        File file = new File("/tmp/twisk/client.c");
        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            // Vérifier si le contenu est égal à "test"
            assertEquals("test", content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
