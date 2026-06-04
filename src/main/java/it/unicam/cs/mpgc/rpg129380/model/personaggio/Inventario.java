package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Oggetto> oggetti;

    public Inventario(Oggetto[] inventario) {
        this.oggetti = new ArrayList<>();
        if (inventario != null) {
            for (Oggetto o : inventario) {
                if (o != null) this.oggetti.add(o);
            }
        }
    }

    public void setInventario(Oggetto[] arr) {
        this.oggetti = new ArrayList<>();
        if (arr != null) {
            for (Oggetto o : arr) {
                if (o != null) this.oggetti.add(o);
            }
        }
    }

    public Inventario() {
        this.oggetti = new ArrayList<>();
    }

    public List<Oggetto> getOggetti() {
        return oggetti;
    }

    public Oggetto[] getInventario() {
        return oggetti.toArray(new Oggetto[0]);
    }

    public void aggiungi(Oggetto o) {
        oggetti.add(o);
    }

    public boolean rimuovi(Oggetto o) {
        return oggetti.remove(o);
    }

}
