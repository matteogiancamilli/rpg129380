package model.classi;

import interfaces.Classe;
import model.personaggio.Abilita;

public class Mago extends Classe {


    public Mago() {
        super("Mago", 200, 2, 2, abilitaIniziali());
    }

    private static Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Palla di Fuoco",
                        "Il mago lancia una sfera di fuoco contro il nemico.",
                        30, 0, 10, 0, 0),
                new Abilita("Meditazione",
                        "Il mago medita, recuperando vita e mana senza costi.",
                        -35, 0, 0, 1, 30),
                new Abilita("Cura",
                        "Il mago canalizza energia guaritrice su se stesso.",
                        -60, 0, 25, 2, 0),
                new Abilita("Stella Cadente",
                        "Il mago invoca una stella cadente che schiaccia il nemico.",
                        85, 0, 45, 3, 0)
        };
    }
}
