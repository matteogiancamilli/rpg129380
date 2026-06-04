package it.unicam.cs.mpgc.rpg129380.interfaces;

public abstract class Persona {

    private String nome;

    public Persona(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
