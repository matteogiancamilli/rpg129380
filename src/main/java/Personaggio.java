public class Personaggio extends Persona {

    private int vita;
    private int livello;
    private Inventario inventario;
    private Classe classe;
    private Abilita[] abilitas;

    public Personaggio(String nome, int vita, int livello, Inventario inventario, Classe classe, Abilita[] abilitas) {
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

    public Classe getClasse() {
        return classe;
    }

    public Abilita[] getAbilitas() {
        return abilitas;
    }
}