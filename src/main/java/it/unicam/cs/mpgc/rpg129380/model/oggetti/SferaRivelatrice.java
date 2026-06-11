package it.unicam.cs.mpgc.rpg129380.model.oggetti;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

public class SferaRivelatrice extends OggettoDati {
    private int parametro;

    @Override
    public void applicaEffetto(Personaggio p, Nemico n) {
        p.aggiungiBonusAttacco(parametro);
        p.subisciDanno(20);
    }
}
