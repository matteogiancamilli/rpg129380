package model;

public class Salvataggio {

    public String nomePersonaggio;
    public TipoClasse classe;
    public int livelloPersonaggio;
    public Inventario inventario;

    public Salvataggio() {}

    public Salvataggio(String nomePersonaggio, TipoClasse classe, Inventario inventario, int livelloPersonaggio) {
            this.nomePersonaggio = nomePersonaggio;
            this.classe = classe;
            this.inventario = inventario;
            this.livelloPersonaggio = livelloPersonaggio;
    }

    public String getNomePersonaggio() {
        return nomePersonaggio;
    }

    public TipoClasse getClasse() {
        return classe;
    }


    public int getLivelloPersonaggio() {
        return livelloPersonaggio;
    }

    public Inventario getInventario(){ return inventario;}
}