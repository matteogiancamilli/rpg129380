package model;

public class Personaggio extends Persona {

    private int vita;
    private int vitaMax;
    private int mana;
    private int manaMax;
    private int livello;
    private Inventario inventario;
    private TipoClasse classe;
    private Abilita[] abilitas;

    public Personaggio(String nome, int vita,int manaMax, int livello, Inventario inventario, TipoClasse classe, Abilita[] abilitas) {
        super(nome);
        this.vitaMax  = vita;
        this.vita     = vita;
        this.manaMax  = classe.getMana();
        this.mana     = manaMax;
        this.livello  = livello;
        this.inventario = inventario;
        this.classe   = classe;
        this.abilitas = abilitas;
    }

    public int getVita()    { return vita; }
    public int getVitaMax() { return vitaMax; }

    public void setVita(int vita) { this.vita = vita; }

    public void subisciDanno(int danno) {
        this.vita = Math.max(0, this.vita - danno);
    }

    public void cura(int quantita) {
        this.vita = Math.min(vitaMax, this.vita + quantita);
    }

    public boolean isSconfitto() {
        return vita <= 0;
    }

    public int getMana()    { return mana; }
    public int getManaMax() { return manaMax; }

    public boolean usaMana(int costo) {
        if (mana < costo) return false;
        mana -= costo;
        return true;
    }

    public void ripristinaMana(int quantita) {
        mana = Math.min(manaMax, mana + quantita);
    }

    public int getLivello() { return livello; }
    public void setLivello(int livello) { this.livello = livello; }

    public void aumentaLivello() {
        this.livello++;
        this.vitaMax  = (int)(vitaMax  * 1.1);
        this.manaMax  += 5;
        this.vita     = vitaMax;
        this.mana     = manaMax;
        System.out.println(getNome() + " è salito al livello " + this.livello + "!");
    }

    public Inventario  getInventario() { return inventario; }
    public TipoClasse  getClasse()     { return classe; }
    public Abilita[]   getAbilitas()   { return abilitas; }

    public void setClasse(TipoClasse classe) { this.classe = classe; }
}