package model;

public abstract class Classe {
    /*
    Classi possibili: model.Guerriero, model.Arciere, model.Mago

    model.Guerriero = + Vita, /Attacco, -Velocita - 1
    model.Arciere = - Vita, +Attacco, +Velocita - 2
    model.Mago = /Vita, /Attacco, /Velocita - 3

     */

    private final String nome;
    private final int vita;
    private final int attacco;
    private final int velocita;
    private Abilita[] abilitas;

    public Classe(String nome, int vita, int attacco, int velocita) {
        this.nome = nome;
        this.vita = vita;
        this.attacco = attacco;
        this.velocita = velocita;
        this.abilitas = abilitaIniziali();
    }

    public abstract Abilita[] abilitaIniziali();

    @Override
    public String toString() {
        return "model.Classe: " + nome + "/ Vita: " + vita + " / + Attacco: " + attacco + " / Velocità: " + velocita;
    }

    public String getNome() {
        return nome;
    }

    public int getVita() {
        return vita;
    }

    public int getAttacco() {
        return attacco;
    }

    public int getVelocita() {
        return velocita;
    }












}
