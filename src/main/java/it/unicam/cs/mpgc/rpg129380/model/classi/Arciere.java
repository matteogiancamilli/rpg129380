package it.unicam.cs.mpgc.rpg129380.model.classi;

import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;

public class Arciere extends Classe {

    public Arciere() {
        super("Arciere", 100, 3, 3, abilitaIniziali());
    }

    private static Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Scoccata",
                        "L'arciere scocca una freccia precisa e rapida.",
                        35, 0, 10, 0, 0),
                new Abilita("Silenzio",
                        "L'arciere si mimetizza nell'ombra, recuperando mana.",
                        0, 20, 15, 1, 30),
                new Abilita("Cura Veloce",
                        "L'arciere beve una pozione al volo, recuperando vita.",
                        -25, 0, 10, 1, 0),
                new Abilita("Megafreccia",
                        "L'arciere carica e scaglia una freccia colossale.",
                        90, 0, 55, 3, 0)
        };
    }
}
