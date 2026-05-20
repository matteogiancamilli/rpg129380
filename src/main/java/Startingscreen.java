import java.io.IOException;
import java.util.Scanner;

public class Startingscreen {

    public static final Scanner scanner = new Scanner(System.in);

    private final CreatoreSalvataggi creatoreSalvataggi = new CreatoreSalvataggi();
    private final CreatorePersonaggi creatorePersonaggio = new CreatorePersonaggi();
    private final InputReader inputReader = new InputReader();
    private final VisualizzatoreMenu visualizzatoreMenu = new VisualizzatoreMenu();

    public void startingScreen() throws IOException {
        visualizzatoreMenu.visualizzaMenuIniziale();
        menuReader();
    }

    public void menuReader() throws IOException {
        while (true) {
            int option = inputReader.leggiIntero();
            switch (option) {
                case 1:
                    if (creatoreSalvataggi.esisteSalvataggio()) {
                        creatoreSalvataggi.resetSalvataggio();
                    }
                    Personaggio personaggio = creatorePersonaggio.creazionePersonaggio();
                    // Salvataggio iniziale (livello 1)
                    creatoreSalvataggi.salva(personaggio);

                    // Avvio del gioco con i 5 livelli
                    new Gioco(personaggio, creatoreSalvataggi).avvia();
                    return;
                case 2:
                    continuaPartita();
                    return;
                case 3:
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Inserisci un numero valido");
            }
        }
    }

    private void continuaPartita() throws IOException {
        if (!creatoreSalvataggi.esisteSalvataggio()) {
            System.out.println("Nessun salvataggio trovato. Inizia una nuova partita.");
            creatorePersonaggio.creazionePersonaggio();
            return;
        }
        Personaggio personaggio = creatoreSalvataggi.carica();
        if (personaggio == null) {
            System.out.println("Salvataggio corrotto. Inizia una nuova partita.");
            creatorePersonaggio.creazionePersonaggio();
            return;
        }
        System.out.println("Partita caricata correttamente!");
        new Gioco(personaggio, creatoreSalvataggi).avvia();
    }
}