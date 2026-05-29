package model;

import java.io.IOException;

public class Gioco {

    public static final int LIVELLO_MAX = Livello.values().length;

    private final Personaggio personaggio;
    private final GestoreSalvataggi saveManager;
    private final InputReader inputReader = new InputReader();

    public Gioco(Personaggio personaggio, GestoreSalvataggi saveManager) {
        this.personaggio = personaggio;
        this.saveManager = saveManager;
    }

    public void avvia() throws IOException {
        System.out.println("\n=== INIZIO AVVENTURA ===");
        System.out.println("model.Personaggio: " + personaggio.getNome()
                + " (" + (personaggio.getClasse() != null ? personaggio.getClasse().getNome() : "?") + ")");
        System.out.println("Riprendi dal livello: " + personaggio.getLivello() + "/" + LIVELLO_MAX);

        while (personaggio.getLivello() <= LIVELLO_MAX) {
            giocaLivello(personaggio.getLivello());
            saveManager.salva(personaggio);

            if (personaggio.getLivello() >= LIVELLO_MAX) {
                System.out.println("\n Hai completato tutti e " + LIVELLO_MAX + " i livelli! Complimenti!");
                saveManager.resetSalvataggio();
                return;
            }

            personaggio.setLivello(personaggio.getLivello() + 1);

            System.out.print("Vuoi continuare al prossimo livello? (s/n): ");
            String scelta = inputReader.leggiStringa().trim().toLowerCase();
            if (!scelta.equals("s")) {
                saveManager.salva(personaggio);
                System.out.println("Partita salvata. A presto!");
                return;
            }
        }
    }

    private void giocaLivello(int livello) {
        Livello livelloCorrente = Livello.values()[livello-1];
        System.out.println("\n--- LIVELLO " + livello + " ---");
        System.out.println(livelloCorrente.getIntroduzione());
        Missione missione = new Missione(livello);
        missione.completaMissione();

        System.out.println("model.Livello " + livello + " completato");
    }


}