package model;

public class Missione {

    private final Nemico mostro;
    private final int id;

    public Missione(int livello, Personaggio personaggio){
        this.id = livello;
        Mostro mostro;
        if (livello >= 0 && livello < Mostro.values().length){
            mostro = Mostro.values()[livello];
        } else {
            mostro = Mostro.GOBLIN;
        }
        this.mostro = new Nemico(mostro);
    }

    public Nemico getMostro(){
        return mostro;
    }

    public boolean isCompletata(){
        return mostro.sconfitto();
    }

    public int getId(){
        return id;
    }
}