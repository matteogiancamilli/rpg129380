package model;

public class Salvataggio {

    public String nomePersonaggio;
    public String classe;
    public int vita;
    public int livelloGioco;

    public Salvataggio() {}

    public Salvataggio(String nomePersonaggio, String classe, int vita, int livelloGioco) {
            this.nomePersonaggio = nomePersonaggio;
            this.classe = classe;
            this.vita = vita;
            this.livelloGioco = livelloGioco;
    }

    public String getNomePersonaggio() {
        return nomePersonaggio;
    }

    public String getClasse() {
        return classe;
    }

    public int getVita() {
        return vita;
    }

    public int getLivelloGioco() {
        return livelloGioco;
    }
}
