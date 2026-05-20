import java.io.IOException;
import java.util.Scanner;

public class CreatorePersonaggi {

    private final InputReader inputReader = new InputReader();
    private final Storia storia = new Storia();
    private final CreatoreSalvataggi creatoreSalvataggi = new CreatoreSalvataggi();
    private final VisualizzatoreMenu visualizzatoreMenu = new VisualizzatoreMenu();

    public Personaggio creazionePersonaggio() throws IOException {
        System.out.print("Inserisci il nome del personaggio: ");
        String nome = inputReader.leggiStringa().trim();
        while (nome.isEmpty()) {
            System.out.print("Il nome non può essere vuoto. Riprova: ");
            nome = inputReader.leggiStringa().trim();
        }

        Personaggio personaggio = new Personaggio(nome, 100, 1, new Inventario(new Oggetto[0]), null, null);
        visualizzatoreMenu.stampaSceltaClassi(nome);

        while (true) {
            int classId = inputReader.leggiIntero();
            switch (classId) {
                case 1: personaggio.setClasse(new Guerriero()); break;
                case 2: personaggio.setClasse(new Arciere()); break;
                case 3: personaggio.setClasse(new Mago()); break;
                default:
                    System.out.println("Inserisci un numero valido");
                    continue;
            }
            System.out.println("Personaggio creato: " + personaggio.getNome());
            storia.intro();
            return personaggio;
        }
    }



}
