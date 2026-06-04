package it.unicam.cs.mpgc.rpg129380.model.classi;

import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;

public class Guerriero extends Classe {

    public Guerriero() {
        super("Guerriero", 300, 2, 1, abilitaIniziali());
    }

    private static Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Fendente",
                        "Il guerriero sferra un fendente con la spada.",
                        30, 0, 10, 0, 0),
                new Abilita("Liberazione",
                        "Il guerriero si alleggerisce, recuperando mana e velocità.",
                        0, 20, 15, 1, 25),
                new Abilita("Rinforzo",
                        "Il guerriero si concentra e ripristina parte della vita.",
                        -40, 0, 15, 2, 0),
                new Abilita("Superspada",
                        "Un fendente devastante che infligge danni enormi.",
                        80, 0, 40, 3, 0)
        };
    }
}
