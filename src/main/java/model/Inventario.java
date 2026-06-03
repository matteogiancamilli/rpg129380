package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inventario {

    private List<Oggetto> inventario;

    /** Costruttore usato dalla deserializzazione Gson (array null-safe). */
    public Inventario(Oggetto[] inventario) {
        this.inventario = new ArrayList<>();
        if (inventario != null) {
            for (Oggetto o : inventario) {
                if (o != null) this.inventario.add(o);
            }
        }
    }

    /** Costruttore vuoto per nuovo gioco. */
    public Inventario() {
        this.inventario = new ArrayList<>();
    }

    // ── Accesso ───────────────────────────────────────────

    public List<Oggetto> getOggetti() {
        return inventario;
    }

    /** Compatibilità con Gson / codice esistente che usa array. */
    public Oggetto[] getInventario() {
        return inventario.toArray(new Oggetto[0]);
    }

    public void setInventario(Oggetto[] arr) {
        this.inventario = new ArrayList<>();
        if (arr != null) {
            for (Oggetto o : arr) {
                if (o != null) this.inventario.add(o);
            }
        }
    }

    // ── Modifica ──────────────────────────────────────────

    public void aggiungi(Oggetto o) {
        inventario.add(o);
    }

    /**
     * Rimuove la prima occorrenza dell'oggetto specificato.
     * @return true se l'oggetto era presente ed è stato rimosso.
     */
    public boolean rimuovi(Oggetto o) {
        return inventario.remove(o);
    }

    // ── Drop casuale dopo il combattimento ────────────────

    /**
     * Restituisce un oggetto casuale usando probabilità pesate per tipo.
     * Gli oggetti rari (tipo 5) hanno probabilità molto più bassa.
     */
    public static Oggetto dropCasuale() {
        Random rng = new Random();
        Oggetto[] tutti = Oggetto.values();

        // Pesi: tipo 5 (raro) → peso 1; tutti gli altri → peso 10
        int[] pesi = new int[tutti.length];
        int totale = 0;
        for (int i = 0; i < tutti.length; i++) {
            pesi[i] = (tutti[i].getTipo() == 5) ? 1 : 10;
            totale += pesi[i];
        }

        int dado = rng.nextInt(totale);
        int accumulato = 0;
        for (int i = 0; i < tutti.length; i++) {
            accumulato += pesi[i];
            if (dado < accumulato) return tutti[i];
        }
        return tutti[0]; // fallback
    }
}
