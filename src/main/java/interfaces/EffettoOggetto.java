package interfaces;

import model.nemici.Nemico;
import model.personaggio.Personaggio;

@FunctionalInterface
public interface EffettoOggetto {
    void applica(Personaggio personaggio, Nemico nemico);
}
