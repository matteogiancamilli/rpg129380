package model;

import java.util.Scanner;

public class InputReader {

    private final static Scanner scanner = new Scanner(System.in);

    public int leggiIntero() {
        while (true) {
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException(
                        "Input non disponibile. Esegui il programma da una console interattiva.");
            }
            String riga = scanner.nextLine().trim();
            try {
                return Integer.parseInt(riga);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido");
            }
        }
    }

    public String leggiStringa() {
        return scanner.nextLine();
    }
}
