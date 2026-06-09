package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import it.unicam.cs.mpgc.rpg129380.model.registry.RegistroOggetti;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<String> chiavi;

    public Inventario(){
        this.chiavi = new ArrayList<>();
    }

    public Inventario(OggettoDati[] iniziali){
        this.chiavi = new ArrayList<>();
        if (iniziali != null) {
            for (OggettoDati o : iniziali) {
                if (o != null) chiavi.add(o.getChiave());
            }
        }
    }

    public void aggiungi(OggettoDati o){
        chiavi.add(o.getChiave());
    }

    public boolean rimuovi(OggettoDati o){
        return chiavi.remove(o.getChiave());
    }

    public List<OggettoDati> getOggetti(){
        List<OggettoDati> lista = new ArrayList<>();
        for (String chiave : chiavi) {
            lista.add(RegistroOggetti.get().daChiave(chiave));
        }
        return lista;
    }

    public List<String> getChiavi(){
        return chiavi;
    }

    public void setChiavi(List<String> chiavi){
        this.chiavi = chiavi != null ? new ArrayList<>(chiavi) : new ArrayList<>();
    }
}