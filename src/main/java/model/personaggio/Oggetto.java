package model.personaggio;

import interfaces.EffettoOggetto;
import model.nemici.Nemico;

public enum Oggetto {
    POZIONESALVAVITA(1, "Ripristina 50 di vita", TipoOggetto.CURA, (p, n) -> p.cura(50)),

    POZIONEDELDRAGO(2, "Infligge 30 di danno", TipoOggetto.DANNO, (p,n) -> n.subisciDanno(30)),

    SCUDODELPALADINO(3, "Aumenta del 30% la difesa massima", TipoOggetto.DIFESA, (p,n) -> p.aggiungiBonusDifesa(30)),

    SPADADELLAROCCIA(4, "Aumenta del 30% l'attacco massimo", TipoOggetto.ATTACCO, (p,n) -> p.aggiungiBonusAttacco(30)),

    GIGAPOZIONE(5, "Aumenta del 30% tutti i campi e li ripristina al massimo", TipoOggetto.RARO, (p,n) -> {
        p.aggiungiBonusDifesa(30);
        p.aggiungiBonusAttacco(30);
        p.cura(p.getVitaMax());
    }),

    SFERARIVELATRICE(6, "Aumenta del 50% l'attacco massimo ma diminuisce di 20 la vita", TipoOggetto.ATTACCO, (p,n) -> {
        p.aggiungiBonusAttacco(50);
        p.subisciDanno(20);
    });

    private final int id;
    private final String descrizione;
    private final TipoOggetto tipo;
    private final EffettoOggetto effetto;

    Oggetto(int id, String descrizione, TipoOggetto tipo, EffettoOggetto effetto) {
        this.id = id;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.effetto = effetto;
    }

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public TipoOggetto getTipo() {
        return tipo;
    }

    public String getNomeVisuale() {
        return switch (this) {
            case POZIONESALVAVITA -> "Pozione Salva Vita";
            case POZIONEDELDRAGO  -> "Pozione del Drago";
            case SCUDODELPALADINO -> "Scudo del Paladino";
            case SPADADELLAROCCIA -> "Spada della Roccia";
            case GIGAPOZIONE      -> "Giga Pozione";
            case SFERARIVELATRICE -> "Sfera Rivelatrice";
        };
    }

    public void applicaEffetto(Personaggio p, Nemico n) {
        effetto.applica(p, n);
    }

    public TipoOggetto getTipoOggetto() {
        return tipo;
    }
}