package it.unicam.cs.mpgc.rpg129380.model.oggetti;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;

public class GigaPozione extends OggettoDati {
    private int parametro;

    @Override
    public void applicaEffetto(Personaggio p, Nemico n) {
        p.aggiungiBonusDifesa(parametro);
        p.aggiungiBonusAttacco(parametro);
        p.cura(p.getVitaMax());
    }
}