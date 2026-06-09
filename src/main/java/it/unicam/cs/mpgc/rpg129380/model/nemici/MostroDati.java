package it.unicam.cs.mpgc.rpg129380.model.nemici;

import java.util.Random;

public class MostroDati {

    private static final Random RNG = new Random();

    private int id;
    private String chiave;
    private String nome;
    private int vitaMassima;
    private int attacco;
    private double moltiplicatoreMassimo;
    private String introduzione;

    public MostroDati() {}

    public MostroDati(int id, String chiave, String nome, int vitaMassima, int attacco, double moltiplicatoreMassimo, String introduzione) {
        this.id = id;
        this.chiave = chiave;
        this.nome = nome;
        this.vitaMassima = vitaMassima;
        this.attacco = attacco;
        this.moltiplicatoreMassimo = moltiplicatoreMassimo;
        this.introduzione = introduzione;
    }

    public int getAttaccoEffettivo() {
        double mult = 1.0 + RNG.nextDouble() * (moltiplicatoreMassimo - 1.0);
        return (int) (attacco * mult);
    }

    public int getId(){
        return id;
    }

    public String getChiave(){
        return chiave;
    }

    public String getNome(){
        return nome;
    }

    public int getVitaMassima(){
        return vitaMassima;
    }

    public int getAttacco(){
        return attacco;
    }

    public double getMoltiplicatoreMassimo(){
        return moltiplicatoreMassimo;
    }

    public String getIntroduzione(){
        return introduzione;
    }
}