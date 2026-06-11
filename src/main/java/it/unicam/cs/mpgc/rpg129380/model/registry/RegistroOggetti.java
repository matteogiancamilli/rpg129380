package it.unicam.cs.mpgc.rpg129380.model.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg129380.model.oggetti.OggettoDati;
import it.unicam.cs.mpgc.rpg129380.model.oggetti.OggettoAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class RegistroOggetti {

    private static final String PERCORSO = "/data/oggetti.json";

    private final Map<String, OggettoDati> perChiave = new LinkedHashMap<>();
    private final List<OggettoDati> inOrdine = new ArrayList<>();

    private RegistroOggetti() {
        carica();
    }

    private static final class Holder {
        static final RegistroOggetti ISTANZA = new RegistroOggetti();
    }

    public static RegistroOggetti get() {
        return Holder.ISTANZA;
    }

    private void carica() {
        try (InputStream is = getClass().getResourceAsStream(PERCORSO)) {
            if (is == null) {
                throw new RuntimeException("File non trovato: " + PERCORSO);
            }
            String json = new String(is.readAllBytes());

            // Configurazione avanzata di GSON tramite costruttore Builder per registrare il custom adapter
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(OggettoDati.class, new OggettoAdapter())
                    .create();

            OggettoDati[] array = gson.fromJson(json, OggettoDati[].class);
            for (OggettoDati o : array) {
                perChiave.put(o.getChiave(), o);
                inOrdine.add(o);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare " + PERCORSO, e);
        }
    }

    public OggettoDati daChiave(String chiave) {
        OggettoDati o = perChiave.get(chiave);
        if (o == null) throw new IllegalArgumentException("Oggetto sconosciuto: " + chiave);
        return o;
    }

    public List<OggettoDati> tutti() {
        return Collections.unmodifiableList(inOrdine);
    }

    public int dimensione() {
        return inOrdine.size();
    }
}