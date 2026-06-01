package model;

public class Salvataggio {

    public String nomePersonaggio;
    public TipoClasse classe;
    public int livelloGioco; // This seems to be the game level, not character level
    public int vitaPersonaggio; // Added for character's current health
    public int livelloPersonaggio; // Added for character's current level
    public Inventario inventario;

    public Salvataggio() {}

    public Salvataggio(String nomePersonaggio, TipoClasse classe, Inventario inventario, int livelloGioco, int vitaPersonaggio, int livelloPersonaggio) {
            this.nomePersonaggio = nomePersonaggio;
            this.classe = classe;
            this.inventario = inventario;
            this.livelloGioco = livelloGioco;
            this.vitaPersonaggio = vitaPersonaggio;
            this.livelloPersonaggio = livelloPersonaggio;
    }

    public String getNomePersonaggio() {
        return nomePersonaggio;
    }

    public TipoClasse getClasse() {
        return classe;
    }

    public int getLivelloGioco() {
        return livelloGioco;
    }

    public int getVitaPersonaggio() {
        return vitaPersonaggio;
    }

    public int getLivelloPersonaggio() {
        return livelloPersonaggio;
    }

    public Inventario getInventario(){ return inventario;}
}