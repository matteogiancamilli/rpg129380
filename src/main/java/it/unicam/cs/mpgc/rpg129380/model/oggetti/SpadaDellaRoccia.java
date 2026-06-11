package it.unicam.cs.mpgc.rpg129380.model.oggetti;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;

public class SpadaDellaRoccia extends OggettoDati {
    private int parametro;

    @Override
    public void applicaEffetto(Personaggio p, Nemico n) {
        p.aggiungiBonusAttacco(parametro);
    }
}