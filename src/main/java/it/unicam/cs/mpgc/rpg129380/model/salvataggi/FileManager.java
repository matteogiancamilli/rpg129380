package it.unicam.cs.mpgc.rpg129380.model.salvataggi;
import java.io.*;
import java.nio.file.*;

public class FileManager {
    public static void scriviFile(String percorso, String contenuto) throws IOException {
        Files.writeString(Paths.get(percorso), contenuto);
    }

    public static String leggiFile(String percorso) throws IOException {
        return Files.readString(Paths.get(percorso));
    }

    public static boolean esiste(String percorso) {
        return Files.exists(Paths.get(percorso));
    }

    public static void elimina(String percorso) throws IOException {
        Files.deleteIfExists(Paths.get(percorso));
    }
}