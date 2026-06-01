package model;

import model.Classi.Arciere;
import model.Classi.Classe;
import model.Classi.Guerriero;
import model.Classi.Mago;

import java.util.function.Supplier;

public enum TipoClasse {
    GUERRIERO("Guerriero", "Alta Vita / Moderato Attacco / Bassa Velocità", Guerriero::new, 400 ,1, 20),
    ARCIERE  ("Arciere",   "Bassa Vita / Alto Attacco / Alta Velocità",     Arciere::new, 200, 3, 20 ),
    MAGO     ("Mago",      "Media Vita / Medio Attacco / Media Velocità",   Mago::new, 300, 2, 20);

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
        throw new IllegalArgumentException("model.Classi.Classe non valida: " + nome);
    }


}