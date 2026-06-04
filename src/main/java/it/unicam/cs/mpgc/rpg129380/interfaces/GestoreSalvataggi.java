package it.unicam.cs.mpgc.rpg129380.interfaces;

import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.IOException;

public interface GestoreSalvataggi extends PersistenzaSalvataggio {
    void nuovo(Personaggio p) throws IOException;
    void resetSalvataggio() throws IOException;
}