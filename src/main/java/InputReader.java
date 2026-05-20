import java.util.Scanner;

public class InputReader {

    Scanner scanner = new Scanner(System.in);

    public int leggiIntero() {
        while (true) {
            if (!scanner.hasNextLine()) {
                // System.in chiuso o EOF: evita il NoSuchElementException
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
