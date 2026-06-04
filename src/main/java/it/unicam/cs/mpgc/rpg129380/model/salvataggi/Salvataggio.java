package it.unicam.cs.mpgc.rpg129380.model.salvataggi;

import it.unicam.cs.mpgc.rpg129380.model.classi.TipoClasse;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Inventario;

public class Salvataggio {

    private String nomePersonaggio;
    private TipoClasse classe;
    private int livelloPersonaggio;
    private Inventario inventario;
    private int vitaMax;
    private int manaMax;

    public Salvataggio() {}

    public Salvataggio(String nomePersonaggio, TipoClasse classe, Inventario inventario, int livelloPersonaggio, int vitaMax, int manaMax) {
            this.nomePersonaggio = nomePersonaggio;
            this.classe = classe;
            this.inventario = inventario;
            this.livelloPersonaggio = livelloPersonaggio;
            this.vitaMax = vitaMax;
            this.manaMax = manaMax;

    }

    public String getNomePersonaggio() {
        return nomePersonaggio;
    }

    public TipoClasse getClasse() {
        return classe;
    }


    public int getLivelloPersonaggio() {
        return livelloPersonaggio;
    }

    public Inventario getInventario(){ return inventario;}

    public int getVitaMax() {
        return vitaMax;
    }

    public int getManaMax() {
        return manaMax;
    }
}