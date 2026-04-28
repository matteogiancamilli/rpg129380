import java.io.IOException;
import java.util.Scanner;

public class Startingscreen {

    /**
     * Scanner condiviso da TUTTA l'applicazione.
     * IMPORTANTE: non deve mai essere chiuso (chiuderebbe System.in)
     * e nessun'altra classe deve creare un nuovo Scanner(System.in):
     * usate sempre questo riferimento.
     */
    public static final Scanner scanner = new Scanner(System.in);

    private final CreatoreSalvataggi saveManager = new CreatoreSalvataggi();

    public void startingScreen() throws IOException {
        System.out.println("Benvenuto in ...");
        System.out.println("Menu di gioco (Scrivi il numero corrispondente e premi invio per accedere alla sezione): ");
        System.out.println("1 - Nuova Partita");
        System.out.println("2 - Continua Partita");
        System.out.println("3 - Esci dal gioco");
        menuReader();
    }

    public void menuReader() throws IOException {
        while (true) {
            int option = leggiIntero();
            switch (option) {
                case 1:
                    if (saveManager.esisteSalvataggio()) {
                        saveManager.resetSalvataggio();
                    }
                    creazionePersonaggio();
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
        if (!saveManager.esisteSalvataggio()) {
            System.out.println("Nessun salvataggio trovato. Inizia una nuova partita.");
            creazionePersonaggio();
            return;
        }
        Personaggio personaggio = saveManager.carica();
        if (personaggio == null) {
            System.out.println("Salvataggio corrotto. Inizia una nuova partita.");
            creazionePersonaggio();
            return;
        }
        System.out.println("Partita caricata correttamente!");
        new Gioco(personaggio, saveManager).avvia();
    }

    private void creazionePersonaggio() throws IOException {
        System.out.print("Inserisci il nome del personaggio: ");
        String nome = scanner.nextLine().trim();
        while (nome.isEmpty()) {
            System.out.print("Il nome non può essere vuoto. Riprova: ");
            nome = scanner.nextLine().trim();
        }

        Personaggio personaggio = new Personaggio(nome, 100, 1, new Inventario(new Oggetto[0]), null, null);
        System.out.println("Personaggio creato: " + personaggio.getNome());
        System.out.println("Scegli la classe del personaggio: ");
        System.out.println("1 - Guerriero: Alta Vita/Moderato Attacco/Bassa Velocità");
        System.out.println("2 - Arciere: Bassa Vita/Alto Attacco/Alta Velocità");
        System.out.println("3 - Mago: Media Vita/Medio Attacco/Media Velocità");

        while (true) {
            int classId = leggiIntero();
            switch (classId) {
                case 1: personaggio.setClasse(new Guerriero()); break;
                case 2: personaggio.setClasse(new Arciere()); break;
                case 3: personaggio.setClasse(new Mago()); break;
                default:
                    System.out.println("Inserisci un numero valido");
                    continue;
            }
            System.out.println("Personaggio creato: " + personaggio.getNome());
            storyline();

            // Salvataggio iniziale (livello 1)
            saveManager.salva(personaggio);

            // Avvio del gioco con i 5 livelli
            new Gioco(personaggio, saveManager).avvia();
            return;
        }
    }

    /**
     * Legge un intero in modo robusto: usa nextLine() per evitare i tipici
     * problemi di newline residuo lasciati da nextInt(), e ripete la richiesta
     * finché l'utente non inserisce un numero valido.
     */
    private int leggiIntero() {
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

    private void storyline() {
        System.out.println("La storia inizia...");
        System.out.println("...");
        System.out.println("Ti risvegli tra le radici contorte di una foresta sconosciuta, il respiro ancora pesante dopo la leggendaria Battaglia di Varkhûn, il cui eco riecheggia nella tua mente come un incubo lontano." +
                " La tua armatura è segnata, la tua lama sporca di sangue antico, ma non ricordi come sei arrivato fin qui. Intorno a te, gli alberi sembrano sussurrare segreti dimenticati, " +
                "mentre un sentiero appena visibile si apre tra la nebbia. Ogni passo verso casa è un’incognita, e ogni ombra potrebbe celare una minaccia. Creature deformi e occhi luminosi ti osservano nell’oscurità, " +
                "pronte a mettere alla prova ciò che resta della tua forza. " +
                "Il viaggio è appena iniziato… e la foresta non perdona.");
    }
}