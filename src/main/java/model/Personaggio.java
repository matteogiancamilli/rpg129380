package model;

import model.Classi.Classe;

public class Personaggio extends Persona {

    private int vita;
    private int livello;
    private int attacco; // Added attacco field
    private Inventario inventario;
    private TipoClasse classe;
    private Abilita[] abilitas;

    public Personaggio(String nome, int vita, int livello, Inventario inventario, TipoClasse classe, Abilita[] abilitas) {
        super(nome);
        this.vita = vita;
        this.livello = livello;
        this.inventario = inventario;
        this.classe = classe;
        this.abilitas = abilitas;
    }

    public int getVita() {
        return vita;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public TipoClasse getClasse() {
        return classe;
    }

    public Abilita[] getAbilitas() {
        return abilitas;
    }

    public void setClasse(TipoClasse classe) {
        this.classe = classe;
    }

    public int getAttacco() {
        return attacco;
    }

    // New method to increase the character's level
    public void aumentaLivello() {
        this.livello++;
        System.out.println(getNome() + " è salito al livello " + this.livello + "!");
        // You might want to add more logic here, like increasing stats, learning new abilities, etc.
    }
}