package it.unicam.cs.mpgc.rpg129380.interfaces;

import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

public interface Inizializzabile {
    void initDati(Personaggio personaggio, GestoreSalvataggi gestoreSalvataggi);
}