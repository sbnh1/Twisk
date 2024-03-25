package test.java.twisk.monde;

import main.java.twisk.outils.KitC;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testCreerFichier(){
        KitC kitC = new KitC();
        kitC.creerFichier("test");
        File file = new File("/tmp/twisk/client.c");
        assertTrue(file.exists());
    }
    @Test
    public void testCreerFichier2(){
        KitC kitC = new KitC();
        kitC.creerFichier("test");
        File file = new File("/tmp/twisk/client.c");
        String string = new String("test");
        assertEquals(file.toString(), string);
    }
}
