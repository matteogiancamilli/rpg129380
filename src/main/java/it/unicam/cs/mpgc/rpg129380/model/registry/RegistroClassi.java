package it.unicam.cs.mpgc.rpg129380.model.registry;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129380.model.classi.ClasseDati;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class RegistroClassi {

    private static final String PERCORSO = "/data/classi.json";

    private final Map<String, ClasseDati> perChiave = new LinkedHashMap<>();
    private final List<ClasseDati> inOrdine = new ArrayList<>();

    private RegistroClassi() {
        carica();
    }

    private static final class Holder {
        static final RegistroClassi ISTANZA = new RegistroClassi();
    }

    public static RegistroClassi get() {
        return Holder.ISTANZA;
    }

    private void carica() {
        try (InputStream is = getClass().getResourceAsStream(PERCORSO)) {
            if (is == null) {
                throw new RuntimeException("File non trovato: " + PERCORSO);
            }
            String json = new String(is.readAllBytes());
            ClasseDati[] array = new Gson().fromJson(json, ClasseDati[].class);
            for (ClasseDati c : array) {
                perChiave.put(c.getChiave(), c);
                inOrdine.add(c);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare " + PERCORSO, e);
        }
    }

    public ClasseDati daChiave(String chiave) {
        ClasseDati c = perChiave.get(chiave);
        if (c == null) throw new IllegalArgumentException("Classe sconosciuta: " + chiave);
        return c;
    }

    public List<ClasseDati> tutte() {
        return Collections.unmodifiableList(inOrdine);
    }

    public int dimensione() {
        return inOrdine.size();
    }
}