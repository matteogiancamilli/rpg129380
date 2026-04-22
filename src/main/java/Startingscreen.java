import java.util.InputMismatchException;
import java.util.Scanner;

public class Startingscreen {

    private final Scanner scanner = new Scanner(System.in);

    public void startingScreen() {
        System.out.println("Benvenuto in ...");
        System.out.println("Menu di gioco (Scrivi il numero corrispondente e premi invio per accedere alla sezione): ");
        System.out.println("1 - Nuova Partita");
        System.out.println("2 - Continua Partita");
        System.out.println("3 - Esci dal gioco");
        menuReader();
    }

    public void menuReader() {
        while (true) {
            int option;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");
                scanner.nextLine(); // consume invalid input
                continue;
            }
            switch (option) {
                case 1:
                    creazionePersonaggio();
                    return;
                case 2:
                    // TODO: continua partita
                    return;
                case 3:
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Inserisci un numero valido");
            }
        }
    }

    private void creazionePersonaggio() {
        scanner.nextLine(); // consume leftover newline
        System.out.print("Inserisci il nome del personaggio: ");
        String nome = scanner.nextLine();
        Personaggio personaggio = new Personaggio(
                nome,
                100,
                1,
                new Inventario(new Oggetto[0]),
                new Classe(),
                new Abilita[0]
        );
        System.out.println("Personaggio creato: " + personaggio.getNome());
    }
}