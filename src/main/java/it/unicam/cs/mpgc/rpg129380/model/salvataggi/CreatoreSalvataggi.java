package it.unicam.cs.mpgc.rpg129380.model.salvataggi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.unicam.cs.mpgc.rpg129380.model.classi.Classe;
import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import it.unicam.cs.mpgc.rpg129380.model.classi.TipoClasse;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreatoreSalvataggi implements GestoreSalvataggi {

    private static final String FILE_PATH = "savegame.json";
    private final Gson gson;

    public CreatoreSalvataggi(){
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(TipoClasse.class, new TypeAdapter<TipoClasse>(){
            @Override
            public void write(JsonWriter out, TipoClasse value) throws IOException {
                if (value == null){
                    out.nullValue();
                    return;
                }
                out.value(value.getNome());
            }

            @Override
            public TipoClasse read(JsonReader in) throws IOException {
                if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String className = in.nextString();
                return TipoClasse.daNome(className);
            }
        });
        this.gson = gsonBuilder.create();
    }

    public boolean esisteSalvataggio(){
        return Files.exists(Paths.get(FILE_PATH));
    }

    public void nuovo(Personaggio personaggio) throws IOException {
        if (esisteSalvataggio()) {
            resetSalvataggio();
        }
        salva(personaggio);
    }

    public void salva(Personaggio p) throws IOException {
        Salvataggio data = new Salvataggio(p.getNome(), p.getClasse(), p.getInventario(), p.getLivello(), p.getVitaMax(), p.getManaMax());
        String jsonString = gson.toJson(data);
        FileManager.scriviFile(FILE_PATH, jsonString);
    }

    public Personaggio carica() throws IOException {
        if (!FileManager.esiste(FILE_PATH)) return null;
        String jsonString = FileManager.leggiFile(FILE_PATH);
        Salvataggio data = gson.fromJson(jsonString, Salvataggio.class);
        if (data == null) return null;

        TipoClasse tipoClasse = data.getClasse();
        Classe classe = tipoClasse.crea();

        Abilita[] abilita = tipoClasse.abilitaIniziali();

        int vitaMax = data.getVitaMax() > 0 ? data.getVitaMax() : tipoClasse.getVita();
        int manaMax = data.getManaMax() > 0 ? data.getManaMax() : tipoClasse.getMana();
        return new Personaggio(data.getNomePersonaggio(), vitaMax, manaMax, data.getLivelloPersonaggio(), data.getInventario(), tipoClasse, abilita);
    }

    public void resetSalvataggio() throws IOException {
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}