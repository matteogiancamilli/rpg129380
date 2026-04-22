import java.util.ArrayList;
import java.util.List;

public abstract class Classe {

    public Abilita[] abilita;

    /*
    Classi possibili: Guerriero, Arciere, Mago

    Guerriero = + Vita, /Attacco, -Velocita - 1
    Arciere = - Vita, +Attacco, +Velocita - 2
    Mago = /Vita, /Attacco, /Velocita - 3

     */

    private String nome;
    private int vita;
    private int attacco;
    private int velocita;

    public Classe(String nome, int vita, int attacco, int velocita, Abilita[] abilitas) {

    }

    public abstract Abilita[] abilitas();








}
