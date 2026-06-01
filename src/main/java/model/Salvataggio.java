package model;

public class Salvataggio {

    public String nomePersonaggio;
    public String classe;
    public int livelloGioco;
    public Inventario inventario;

    public Salvataggio() {}

    public Salvataggio(String nomePersonaggio, String classe, Inventario inventario, int livelloGioco) {
            this.nomePersonaggio = nomePersonaggio;
            this.classe = classe;
            this.inventario = inventario;
            this.livelloGioco = livelloGioco;
    }

    public String getNomePersonaggio() {
        return nomePersonaggio;
    }

    public String getClasse() {
        return classe;
    }

    public int getLivelloGioco() {
        return livelloGioco;
    }

    public Inventario getInventario(){ return inventario;}
}
