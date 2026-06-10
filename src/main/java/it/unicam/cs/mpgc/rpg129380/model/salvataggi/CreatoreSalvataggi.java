package it.unicam.cs.mpgc.rpg129380.model.salvataggi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import it.unicam.cs.mpgc.rpg129380.model.classi.ClasseDati;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Inventario;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;
import it.unicam.cs.mpgc.rpg129380.model.registry.RegistroClassi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreatoreSalvataggi implements GestoreSalvataggi {

    private static final String FILE_PATH = "savegame.json";
    private final Gson gson;

    public CreatoreSalvataggi() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean esisteSalvataggio() {
        return Files.exists(Paths.get(FILE_PATH));
    }

    @Override
    public void nuovo(Personaggio personaggio) throws IOException {
        if (esisteSalvataggio()) {
            resetSalvataggio();
        }
        salva(personaggio);
    }

    @Override
    public void salva(Personaggio p) throws IOException {
        Salvataggio data = new Salvataggio(
                p.getNome(),
                p.getClasse().getChiave(),
                p.getInventario(),
                p.getLivello(),
                p.getVitaMax(),
                p.getManaMax()
        );
        String jsonString = gson.toJson(data);
        FileManager.scriviFile(FILE_PATH, jsonString);
    }

    @Override
    public Personaggio carica() throws IOException {
        if (!FileManager.esiste(FILE_PATH)) return null;

        String jsonString = FileManager.leggiFile(FILE_PATH);
        Salvataggio data  = gson.fromJson(jsonString, Salvataggio.class);
        if (data == null) return null;

        ClasseDati classe = RegistroClassi.get().daChiave(data.getClasseChiave());
        Abilita[] abilita = classe.abilitaIniziali();

        int vitaMax = data.getVitaMax() > 0 ? data.getVitaMax() : classe.getVita();
        int manaMax = data.getManaMax() > 0 ? data.getManaMax() : classe.getMana();

        Inventario inventario = data.getInventario();
        if (inventario == null) inventario = new Inventario();

        return new Personaggio(
                data.getNomePersonaggio(),
                vitaMax,
                manaMax,
                data.getLivelloPersonaggio(),
                inventario,
                classe,
                abilita
        );
    }

    @Override
    public void resetSalvataggio() throws IOException {
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}