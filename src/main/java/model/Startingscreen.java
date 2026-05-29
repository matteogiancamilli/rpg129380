package model;

import java.io.IOException;

public class Startingscreen {

    private final GestoreSalvataggi creatoreSalvataggi = new CreatoreSalvataggi();
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
                    nuovaPartita();
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

    private void nuovaPartita() throws IOException {
        Personaggio personaggio = creatorePersonaggio.creazionePersonaggio();
        // model.Salvataggio iniziale (livello 1)
        creatoreSalvataggi.nuovo(personaggio);

        // Avvio del gioco con i 5 livelli
        new Gioco(personaggio, creatoreSalvataggi).avvia();

    }

    private void continuaPartita() throws IOException {
        if (!creatoreSalvataggi.esisteSalvataggio()) {
            System.out.println("Nessun salvataggio trovato. Inizia una nuova partita.");
            creatorePersonaggio.creazionePersonaggio();
            return;
        }
        Personaggio personaggio = creatoreSalvataggi.carica();
        if (personaggio == null) {
            System.out.println("model.Salvataggio corrotto. Inizia una nuova partita.");
            creatorePersonaggio.creazionePersonaggio();
            return;
        }
        System.out.println("Partita caricata correttamente!");
        new Gioco(personaggio, creatoreSalvataggi).avvia();
    }
}