package it.unicam.cs.mpgc.rpg129380.model.classi;

import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.AbilitaDati;

import java.util.List;

public class ClasseDati {

    private String chiave;
    private String nome;
    private String descrizione;
    private int vita;
    private int mana;
    private int velocita;
    private List<AbilitaDati> abilita;

    public ClasseDati() {}

    public Abilita[] abilitaIniziali() {
        if (abilita == null) return new Abilita[0];
        return abilita.stream()
                .map(AbilitaDati::toAbilita)
                .toArray(Abilita[]::new);
    }

    public String getChiave(){
        return chiave;
    }

    public String getNome(){
        return nome;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public int getVita(){
        return vita;
    }

    public int getMana(){
        return mana;
    }

    public int getVelocita(){
        return velocita;
    }

    public List<AbilitaDati> getAbilitaDati(){
        return abilita;
    }
}