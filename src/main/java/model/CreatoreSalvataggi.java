package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Classi.Classe;

import java.io.FileReader;
import java.io.FileWriter;
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

    public void salva(Personaggio p){
        Salvataggio data = new Salvataggio(p.getNome(), p.getClasse(), p.getInventario(), p.getLivello(), p.getVitaMax(), p.getManaMax());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public Personaggio carica() throws IOException {
        if (!esisteSalvataggio()) return null;
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Salvataggio data = gson.fromJson(reader, Salvataggio.class);
            if (data == null) return null;

            TipoClasse tipoClasse = data.classe;
            Classe classe = tipoClasse.crea();

            Abilita[] abilitas = classe.abilitaIniziali();

            int vitaMax = data.vitaMax > 0 ? data.vitaMax : tipoClasse.getVita();
            int manaMax = data.manaMax > 0 ? data.manaMax : tipoClasse.getMana();
            return new Personaggio(data.nomePersonaggio, vitaMax, manaMax, data.livelloPersonaggio, data.inventario, tipoClasse, abilitas);
        }
    }

    public void resetSalvataggio() throws IOException {
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}