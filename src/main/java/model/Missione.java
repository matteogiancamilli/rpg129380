package model;

public class Missione {

    private final Mostro mostro;
    private final int id;

    public Missione(int livello, Personaggio personaggio) {
        this.id = livello;

        if (livello >= 0 && livello < Mostro.values().length) {
            this.mostro = Mostro.values()[livello];
        } else {
            System.err.println("Livello missione non valido: " + livello + ", uso GOBLIN.");
            this.mostro = Mostro.GOBLIN;
        }
        this.mostro.resetMostro();
    }

    /** Restituisce il mostro di questa missione, pronto per il combattimento. */
    public Mostro getMostro() {
        return mostro;
    }

    /** True se il mostro è già stato sconfitto. */
    public boolean isCompletata() {
        return mostro.sconfitto();
    }

    public int getId() {
        return id;
    }
}