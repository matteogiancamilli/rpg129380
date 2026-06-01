package model;

import java.io.IOException;

public class CreatorePersonaggi {

    private final InputReader inputReader = new InputReader();
    private final Storia storia = new Storia();

    public Personaggio creazionePersonaggio() throws IOException {
        System.out.print("Inserisci il nome del personaggio: ");
        String nome = inputReader.leggiStringa().trim();
        while (nome.isEmpty()) {
            System.out.print("Il nome non può essere vuoto. Riprova: ");
            nome = inputReader.leggiStringa().trim();
        }

        Personaggio personaggio = new Personaggio(nome, 100, 1, new Inventario(new Oggetto[0]), null, null);

        TipoClasse scelta = null;
        while (scelta == null) {
            try {
                int classId = inputReader.leggiIntero();
                scelta = TipoClasse.daId(classId);
            } catch (IllegalArgumentException e) {
                System.out.println("Scelta non valida. Riprova.");
            }
        }
        personaggio.setClasse(scelta);
        System.out.println("Personaggio creato: " + personaggio.getNome());
        return personaggio;
    }



}
