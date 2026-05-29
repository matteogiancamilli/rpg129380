package model;

import java.util.function.Supplier;

public enum TipoClasse {
    GUERRIERO("model.Guerriero", "Alta Vita / Moderato Attacco / Bassa Velocità", Guerriero::new),
    ARCIERE  ("model.Arciere",   "Bassa Vita / Alto Attacco / Alta Velocità",     Arciere::new),
    MAGO     ("model.Mago",      "Media Vita / Medio Attacco / Media Velocità",   Mago::new);

    private final String nome;
    private final String descrizione;
    private final Supplier<Classe> factory;

    TipoClasse(String nome, String descrizione, Supplier<Classe> factory) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.factory = factory;
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
        throw new IllegalArgumentException("model.Classe non valida: " + nome);
    }
}