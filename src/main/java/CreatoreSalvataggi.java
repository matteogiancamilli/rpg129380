import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreatoreSalvataggi{

    private static final String file = "savegame.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public boolean esisteSalvataggio() {
        return Files.exists(Paths.get(file));
    }

    public void salva(Personaggio p) {
        Salvataggio data = new Salvataggio(p.getNome(), p.getClasse().getNome(), p.getVita(), p.getLivello());
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
            System.out.println("La partita è stata salvata in: " + file);
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public Personaggio carica() throws IOException {
        if (!esisteSalvataggio()) return null;
        FileReader reader = new FileReader(file);
            Salvataggio data = gson.fromJson(reader, Salvataggio.class);
            reader.close();
            if (data == null) return null;


        Classe classe = switch (data.classe) {
            case "Guerriero" -> new Guerriero();
            case "Arciere" -> new Arciere();
            case "Mago" -> new Mago();
            default -> throw new IllegalStateException("Classe non valida: " + data.classe);
        };

        Abilita[] abilitas = classe.abilitaIniziali();

            return new Personaggio(data.nomePersonaggio, data.vita, data.livelloGioco,
                    new Inventario(new Oggetto[0]), classe, abilitas
            );
    }

    public void resetSalvataggio() throws IOException {
            Path path = Paths.get(file);
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Salvataggio precedente eliminato");
            }else{
                System.out.println("Nessun salvataggio precedente");
            }
    }
}
