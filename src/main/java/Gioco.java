
import java.io.IOException;
import java.util.Scanner;

public class Gioco {

    public static final int LIVELLO_MAX = 5;

    private final Personaggio personaggio;
    private final CreatoreSalvataggi saveManager;
    private final InputReader inputReader = new InputReader();

    public Gioco(Personaggio personaggio, CreatoreSalvataggi saveManager) {
        this.personaggio = personaggio;
        this.saveManager = saveManager;
    }

    public void avvia() throws IOException {
        System.out.println("\n=== INIZIO AVVENTURA ===");
        System.out.println("Personaggio: " + personaggio.getNome()
                + " (" + (personaggio.getClasse() != null ? personaggio.getClasse().getNome() : "?") + ")");
        System.out.println("Riprendi dal livello: " + personaggio.getLivello() + "/" + LIVELLO_MAX);

        while (personaggio.getLivello() <= LIVELLO_MAX) {
            giocaLivello(personaggio.getLivello());

            saveManager.salva(personaggio);

            if (personaggio.getLivello() >= LIVELLO_MAX) {
                System.out.println("\n🏆 Hai completato tutti e " + LIVELLO_MAX + " i livelli! Complimenti!");
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
        System.out.println("\n--- LIVELLO " + livello + " ---");
        switch (livello) {
            case 1 -> System.out.println("Attraversi la foresta nebbiosa e sconfiggi un lupo selvatico.");
            case 2 -> System.out.println("Trovi una caverna oscura e affronti uno scheletro guerriero.");
            case 3 -> System.out.println("Raggiungi un villaggio in rovina assediato dai banditi.");
            case 4 -> System.out.println("Sali sulle montagne ghiacciate e combatti un troll di pietra.");
            case 5 -> System.out.println("Affronti il Signore delle Ombre nel suo castello maledetto!");
            default -> System.out.println("Livello sconosciuto.");
        }
        System.out.println("Livello " + livello + " completato");
    }
}