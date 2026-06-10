package it.unicam.cs.mpgc.rpg129380.model.personaggio;

public class AbilitaDati {

    private String nome;
    private String descrizione;
    private int danno;
    private int costoMana;
    private int cooldownMax;
    private int ripristinoMana;
    private int aumentaVelocita;

    public AbilitaDati() {}

    public Abilita toAbilita() {
        return new Abilita(nome, descrizione, danno, aumentaVelocita,
                costoMana, cooldownMax, ripristinoMana);
    }

    public String getNome(){
        return nome;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public int getDanno(){
        return danno;
    }

    public int getCostoMana(){
        return costoMana;
    }

    public int getCooldownMax(){
        return cooldownMax;
    }

    public int getRipristinoMana(){
        return ripristinoMana;
    }

    public int getAumentaVelocita(){
        return aumentaVelocita;
    }
}