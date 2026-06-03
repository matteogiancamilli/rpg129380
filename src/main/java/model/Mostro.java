package model;

import java.util.Random;

public enum Mostro {
    GOBLIN           (1, 1.5, 120,  20, "Goblin",            "Appare un piccolo omuncolo dalle fratte... È un goblin!"),
    SCAGNOZZO        (2, 2.0, 200,  28, "Scagnozzo",         "Da dietro un palazzo, in modo diffidente, ti appare davanti uno scagnozzo di media statura. Non sembra avere buone intenzioni..."),
    APPRENDISTAMAGO  (3, 2.5, 260,  35, "Apprendista Mago",  "Dalla cima della torre, esce un mago pronto a difendere il suo castello!"),
    DRAGODELLAFORESTA(4, 2.5, 350,  42, "Drago della Foresta","Dalla cima della montagna, appare imponente il drago protettore della foresta."),
    SIGNOREOSCURO    (5, 3.0, 500,  50, "Signore Oscuro",    "Ed ecco apparire dalle tenebre colui che hanno tutti temuto... Il Signore Oscuro!");


    private final int id;
    private final String introduzione;
    private final int vitaMassima;
    private final int attacco;
    private final String nome;
    private final double moltiplicatoreMassimo;

    private static final Random RNG = new Random();

    Mostro(int id, double moltiplicatoreMassimo, int vita, int attacco, String nome, String introduzione){
        this.id = id;
        this.moltiplicatoreMassimo = moltiplicatoreMassimo;
        this.vitaMassima = vita;
        this.attacco = attacco;
        this.nome = nome;
        this.introduzione = introduzione;
    }

    public int getAttaccoEffettivo() {
        double mult = 1.0 + RNG.nextDouble() * (moltiplicatoreMassimo - 1.0);
        return (int)(attacco * mult);
    }

    public int getVitaMassima(){
        return vitaMassima;
    }

    public int getAttacco(){
        return attacco;
    }

    public String getNome(){
        return nome;
    }

    public String getIntroduzione(){
        return introduzione;
    }
}