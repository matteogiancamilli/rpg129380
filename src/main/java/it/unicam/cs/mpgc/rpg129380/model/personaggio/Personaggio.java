package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import it.unicam.cs.mpgc.rpg129380.model.classi.Persona;
import it.unicam.cs.mpgc.rpg129380.model.classi.TipoClasse;

public class Personaggio extends Persona {

    private int vita;
    private int vitaMax;
    private int mana;
    private int manaMax;
    private int livello;
    private Inventario inventario;
    private TipoClasse classe;
    private Abilita[] abilitas;

    private int bonusAttaccoPerc  = 0;
    private int bonusDifesaPerc   = 0;
    private static final double MOLTIPLICATORE_VITA_LEVEL_UP = 1.1;
    private static final int    INCREMENTO_MANA_LEVEL_UP     = 5;

    public Personaggio(String nome, int vitaMax, int manaMax, int livello,
                       Inventario inventario, TipoClasse classe, Abilita[] abilitas) {
        super(nome);
        this.vitaMax    = vitaMax;
        this.vita       = vitaMax;
        this.manaMax    = manaMax;
        this.mana       = manaMax;
        this.livello    = livello;
        this.inventario = inventario;
        this.classe     = classe;
        this.abilitas   = abilitas;
    }

    public void subisciDanno(int danno) {
        int dannoEffettivo = danno;
        if (bonusDifesaPerc > 0) {
            dannoEffettivo = (int)(danno * (1.0 - bonusDifesaPerc / 100.0));
            dannoEffettivo = Math.max(0, dannoEffettivo);
        }
        this.vita = Math.max(0, this.vita - dannoEffettivo);
    }

    public void cura(int quantita) {
        this.vita = Math.min(vitaMax, this.vita + quantita);
    }

    public boolean isSconfitto() { return vita <= 0; }

    public void aumentaVitaMax(int quantita) {
        this.vitaMax += quantita;
        this.vita = Math.min(this.vita + quantita, this.vitaMax);
    }

    public boolean usaMana(int costo) {
        if (mana < costo) return false;
        mana -= costo;
        return true;
    }

    public void ripristinaMana(int quantita) {
        mana = Math.min(manaMax, mana + quantita);
    }

    public void aumentaManaMax(int quantita) {
        this.manaMax += quantita;
        this.mana = Math.min(this.mana + quantita, this.manaMax);
    }

    public int calcolaDannoEffettivo(int dannoBase) {
        if (bonusAttaccoPerc > 0) {
            return (int)(dannoBase * (1.0 + bonusAttaccoPerc / 100.0));
        }
        return dannoBase;
    }

    public void aggiungiBonusAttacco(int percento){
        this.bonusAttaccoPerc += percento;
    }
    public void aggiungiBonusDifesa(int percento){
        this.bonusDifesaPerc  += percento;
    }

    public void resettaBonus(){
        this.bonusAttaccoPerc = 0;
        this.bonusDifesaPerc  = 0;
    }

    public void aumentaLivello(){
        this.livello++;
        this.vitaMax = (int)(vitaMax * MOLTIPLICATORE_VITA_LEVEL_UP);
        this.manaMax += INCREMENTO_MANA_LEVEL_UP;
        this.vita    = vitaMax;
        this.mana    = manaMax;
    }

    public void eseguiFineTurno() {
        this.resettaBonus();
        for (Abilita a : this.abilitas) {
            a.tickCooldown();
        }
    }

    public Inventario getInventario(){
        return inventario;
    }

    public TipoClasse getClasse(){
        return classe;
    }

    public Abilita[]  getAbilitas(){
        return abilitas;
    }

    public void setClasse(TipoClasse classe){
        this.classe = classe;
    }

    public int  getLivello(){
        return livello;
    }

    public void setLivello(int livello){
        this.livello = livello;
    }

    public int  getBonusAttaccoPerc(){
        return bonusAttaccoPerc;
    }

    public int  getBonusDifesaPerc(){
        return bonusDifesaPerc;
    }

    public int  getMana(){
        return mana;
    }
    public int  getManaMax(){
        return manaMax;
    }

    public int  getVita(){
        return vita;
    }

    public int  getVitaMax(){
        return vitaMax;
    }

    public void setVita(int vita) {
        this.vita = vita;
    }
}
