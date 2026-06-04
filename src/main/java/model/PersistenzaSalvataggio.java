package model;

import java.io.IOException;

public interface PersistenzaSalvataggio {
    void salva(Personaggio p) throws IOException;
    Personaggio carica() throws IOException;
    boolean esisteSalvataggio();
}
