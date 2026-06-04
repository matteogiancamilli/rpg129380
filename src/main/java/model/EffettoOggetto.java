package model;

@FunctionalInterface
public interface EffettoOggetto {
    void applica(Personaggio personaggio, Nemico nemico);
}
