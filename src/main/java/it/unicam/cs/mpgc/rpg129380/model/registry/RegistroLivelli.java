package it.unicam.cs.mpgc.rpg129380.model.registry;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129380.model.gioco.LivelloDati;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class RegistroLivelli {

    private static final String PERCORSO = "/data/livelli.json";

    private final List<LivelloDati> inOrdine = new ArrayList<>();

    private RegistroLivelli() {
        carica();
    }

    private static final class Holder {
        static final RegistroLivelli ISTANZA = new RegistroLivelli();
    }

    public static RegistroLivelli get() {
        return Holder.ISTANZA;
    }

    private void carica() {
        try (InputStream is = getClass().getResourceAsStream(PERCORSO)) {
            if (is == null) {
                throw new RuntimeException("File non trovato: " + PERCORSO);
            }
            String json = new String(is.readAllBytes());
            LivelloDati[] array = new Gson().fromJson(json, LivelloDati[].class);
            Arrays.sort(array, Comparator.comparingInt(LivelloDati::getId));
            Collections.addAll(inOrdine, array);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare " + PERCORSO, e);
        }
    }

    public LivelloDati daIndice(int indice) {
        if (indice < 0 || indice >= inOrdine.size()) {
            throw new IndexOutOfBoundsException("Indice livello non valido: " + indice);
        }
        return inOrdine.get(indice);
    }

    public int dimensione() {
        return inOrdine.size();
    }

    public List<LivelloDati> tutti() {
        return Collections.unmodifiableList(inOrdine);
    }
}