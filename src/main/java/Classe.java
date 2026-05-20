
public abstract class Classe {
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
    private Abilita[] abilitas;

    public Classe(String nome, int vita, int attacco, int velocita) {
        this.nome = nome;
        this.vita = vita;
        this.attacco = attacco;
        this.velocita = velocita;
        this.abilitas = abilitaIniziali();
    }

    public abstract Abilita[] abilitaIniziali();

    @Override
    public String toString() {
        return "Classe: " + nome + "/ Vita: " + vita + " / + Attacco: " + attacco + " / Velocità: " + velocita;
    }

    public String getNome() {
        return nome;
    }

    public int getVita() {
        return vita;
    }

    public int getAttacco() {
        return attacco;
    }

    public int getVelocita() {
        return velocita;
    }












}
