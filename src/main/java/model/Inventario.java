package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inventario {

    private List<Oggetto> oggetti;

    /** Costruttore usato dalla deserializzazione Gson (array null-safe). */
    public Inventario(Oggetto[] inventario) {
        this.oggetti = new ArrayList<>();
        if (inventario != null) {
            for (Oggetto o : inventario) {
                if (o != null) this.oggetti.add(o);
            }
        }
    }

    /** Costruttore vuoto per nuovo gioco. */
    public Inventario() {
        this.oggetti = new ArrayList<>();
    }

    // ── Accesso ───────────────────────────────────────────

    public List<Oggetto> getOggetti() {
        return oggetti;
    }

    /** Compatibilità con Gson / codice esistente che usa array. */
    public Oggetto[] getInventario() {
        return oggetti.toArray(new Oggetto[0]);
    }

    public void setInventario(Oggetto[] arr) {
        this.oggetti = new ArrayList<>();
        if (arr != null) {
            for (Oggetto o : arr) {
                if (o != null) this.oggetti.add(o);
            }
        }
    }

    // ── Modifica ──────────────────────────────────────────

    public void aggiungi(Oggetto o) {
        oggetti.add(o);
    }

    /**
     * Rimuove la prima occorrenza dell'oggetto specificato.
     * @return true se l'oggetto era presente ed è stato rimosso.
     */
    public boolean rimuovi(Oggetto o) {
        return oggetti.remove(o);
    }


}
