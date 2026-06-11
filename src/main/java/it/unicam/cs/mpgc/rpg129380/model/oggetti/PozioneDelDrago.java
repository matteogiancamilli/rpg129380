package it.unicam.cs.mpgc.rpg129380.model.oggetti;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

public class PozioneDelDrago extends OggettoDati {
    private int parametro;

    @Override
    public void applicaEffetto(Personaggio p, Nemico n) {
        n.subisciDanno(parametro);
    }
}
