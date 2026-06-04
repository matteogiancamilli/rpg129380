package it.unicam.cs.mpgc.rpg129380.model.personaggio;

public class Abilita {

    private final String nome;
    private final String descrizione;
    private final int danno;
    private final int aumentaVelocita;
    private final int costoMana;
    private final int cooldownMax;
    private final int ripristinoMana;
    private int cooldownCorrente;

    public Abilita(String nome, String descrizione, int danno, int aumentaVelocita, int costoMana, int cooldownMax, int ripristinoMana){
        this.nome = nome;
        this.descrizione = descrizione;
        this.danno = danno;
        this.aumentaVelocita = aumentaVelocita;
        this.ripristinoMana = ripristinoMana;
        this.costoMana = costoMana;
        this.cooldownMax = cooldownMax;
        this.cooldownCorrente = 0;
    }

    public String stato(){
        String stato = "";
            if (cooldownCorrente == 0) {
                stato = " [Pronto]";
            } else {
                stato = " [In ricarica: " + cooldownCorrente + "/" + cooldownMax + "]";
            }
            return stato;
    }


    public String getNome(){
        return nome;
    }

    public int getRipristinoMana(){
        return ripristinoMana;
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

    public boolean isCura(){
        return danno < 0;
    }

    public int getAumentaVelocita(){
        return aumentaVelocita;
    }

    public boolean abilitaPronta(){
        return cooldownCorrente == 0;
    }

    public int getCooldownCorrente(){
        return cooldownCorrente;
    }

    public void attivaAbilita(){
        this.cooldownCorrente = cooldownMax;
    }

    public void tickCooldown(){
        if (cooldownCorrente > 0) cooldownCorrente--;
    }
}