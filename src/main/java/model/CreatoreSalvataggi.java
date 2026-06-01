package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Classi.Classe;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreatoreSalvataggi implements GestoreSalvataggi {

    private static final String FILE_PATH = "savegame.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public boolean esisteSalvataggio() {
        return Files.exists(Paths.get(FILE_PATH));
    }

    public void nuovo(Personaggio personaggio) throws IOException {
        if (esisteSalvataggio()) {
            resetSalvataggio();
        }
        salva(personaggio);
    }

    public void salva(Personaggio p) {
        Salvataggio data = new Salvataggio(p.getNome(), p.getClasse().getNome(), p.getInventario(), p.getLivello());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
            System.out.println("La partita è stata salvata in: " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public Personaggio carica() throws IOException {
        if (!esisteSalvataggio()) return null;
        FileReader reader = new FileReader(FILE_PATH);
            Salvataggio data = gson.fromJson(reader, Salvataggio.class);
            reader.close();
            if (data == null) return null;

        String fullClassName = data.classe;
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        Classe classe = TipoClasse.daNome(simpleClassName).crea();

        Abilita[] abilitas = classe.abilitaIniziali();

            return new Personaggio(data.nomePersonaggio, classe.getVita(), data.livelloGioco,
                    new Inventario(new Oggetto[0]), classe, abilitas
            );
    }

    public void resetSalvataggio() throws IOException {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("model.Salvataggio precedente eliminato");
            }else{
                System.out.println("Nessun salvataggio precedente");
            }
    }
}