package it.unicam.cs.mpgc.rpg129380.interfaces;

import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.IOException;

public interface PersistenzaSalvataggio {
    void salva(Personaggio p) throws IOException;
    Personaggio carica() throws IOException;
    boolean esisteSalvataggio();
}
