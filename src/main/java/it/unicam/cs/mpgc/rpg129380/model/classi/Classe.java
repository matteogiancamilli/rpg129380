package it.unicam.cs.mpgc.rpg129380.model.classi;

import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;

public abstract class Classe {

    private final String nome;
    private final int vita;
    private final int attacco;
    private final int velocita;
    private Abilita[] abilita;

    public Classe(String nome, int vita, int attacco, int velocita, Abilita[] abilita) {
        this.nome = nome;
        this.vita = vita;
        this.attacco = attacco;
        this.velocita = velocita;
        this.abilita = abilita;
    }

    @Override
    public String toString(){
        return "Classe: " + nome + "/ Vita: " + vita + " / + Attacco: " + attacco + " / Velocità: " + velocita;
    }

    public String getNome(){
        return nome;
    }

    public int getVita(){
        return vita;
    }

    public int getVelocita(){
        return velocita;
    }

    public Abilita[] getAbilita(){
        return abilita;
    }
}
