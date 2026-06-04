package it.unicam.cs.mpgc.rpg129380.interfaces;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

@FunctionalInterface
public interface EffettoOggetto {
    void applica(Personaggio personaggio, Nemico nemico);
}
