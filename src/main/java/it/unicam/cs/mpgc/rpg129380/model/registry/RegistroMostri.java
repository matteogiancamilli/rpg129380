package it.unicam.cs.mpgc.rpg129380.model.registry;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg129380.model.nemici.MostroDati;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class RegistroMostri {

    private static final String PERCORSO = "/data/mostri.json";

    private final Map<String, MostroDati> perChiave = new LinkedHashMap<>();
    private final List<MostroDati> inOrdine = new ArrayList<>();

    private RegistroMostri() {
        carica();
    }

    private static final class Holder {
        static final RegistroMostri ISTANZA = new RegistroMostri();
    }

    public static RegistroMostri get() {
        return Holder.ISTANZA;
    }

    private void carica() {
        try (InputStream is = getClass().getResourceAsStream(PERCORSO)) {
            if (is == null) {
                throw new RuntimeException("File non trovato: " + PERCORSO);
            }
            String json = new String(is.readAllBytes());
            MostroDati[] array = new Gson().fromJson(json, MostroDati[].class);
            Arrays.sort(array, Comparator.comparingInt(MostroDati::getId));
            for (MostroDati m : array) {
                perChiave.put(m.getChiave(), m);
                inOrdine.add(m);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare " + PERCORSO, e);
        }
    }

    public MostroDati daChiave(String chiave) {
        MostroDati m = perChiave.get(chiave);
        if (m == null) throw new IllegalArgumentException("Mostro sconosciuto: " + chiave);
        return m;
    }

    public MostroDati daIndice(int indice) {
        if (indice < 0 || indice >= inOrdine.size()) {
            throw new IndexOutOfBoundsException("Indice mostro non valido: " + indice);
        }
        return inOrdine.get(indice);
    }

    public int dimensione() {
        return inOrdine.size();
    }

    public List<MostroDati> tutti() {
        return Collections.unmodifiableList(inOrdine);
    }
}