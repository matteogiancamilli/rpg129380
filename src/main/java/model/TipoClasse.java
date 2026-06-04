package model;

import model.Classi.Arciere;
import model.Classi.Classe;
import model.Classi.Guerriero;
import model.Classi.Mago;

import java.util.function.Supplier;

public enum TipoClasse {
    GUERRIERO("Guerriero", "Combatti per il Re e diventa una leggenda!\n - Alta Vita \n - Basso Attaco \n - Bassa Velocità", Guerriero::new, 400, 1, 80),
    ARCIERE  ("Arciere",   "Mostra quanto la tua mira sia precisa!\n - Bassa Vita \n - Alto Attaco \n - Alta Velocità",     Arciere::new,  200, 3, 80),
    MAGO     ("Mago",      "Usa le doti più arcane della magia!\n - Media Vita \n - Medio Attaco \n - Media Velocità",   Mago::new,     300, 2, 80);

    private final String nome;
    private final String descrizione;
    private int vita, velocita, mana;
    private final Supplier<Classe> factory;

    TipoClasse(String nome, String descrizione, Supplier<Classe> factory, int vita, int velocita, int mana) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.factory = factory;
        this.vita = vita;
        this.velocita = velocita;
        this.mana = mana;
    }

    public String getNome()        { return nome; }
    public String getDescrizione() { return descrizione; }
    public Classe crea()           { return factory.get(); }

    public static TipoClasse daId(int id) {     // 1-based per il menu
        if (id < 1 || id > values().length) {
            throw new IllegalArgumentException("Id non valido: " + id);
        }
        return values()[id - 1];
    }

    public static TipoClasse daNome(String nome) {
        for (TipoClasse t : values()) {
            if (t.nome.equalsIgnoreCase(nome)) return t;
        }
        throw new IllegalArgumentException("Classe non valida: " + nome);
    }


    public int getVita() {
        return vita;
    }
    public int getMana() { return mana; }

    public Abilita[] abilitaIniziali() {
        return crea().abilitaIniziali();
    }
}