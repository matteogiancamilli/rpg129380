package model;

import java.io.IOException;

public interface GestoreSalvataggi extends PersistenzaSalvataggio{
    void nuovo(Personaggio p) throws IOException;
    void resetSalvataggio() throws IOException;
}