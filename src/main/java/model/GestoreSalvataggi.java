package model;

import java.io.IOException;

public interface GestoreSalvataggi {
    void nuovo(Personaggio p) throws IOException;
    void salva(Personaggio p);
    Personaggio carica() throws IOException;
    boolean esisteSalvataggio();
    void resetSalvataggio() throws IOException;
}