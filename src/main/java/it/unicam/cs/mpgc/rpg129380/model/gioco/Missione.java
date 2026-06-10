package it.unicam.cs.mpgc.rpg129380.model.gioco;

import it.unicam.cs.mpgc.rpg129380.model.nemici.MostroDati;
import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;
import it.unicam.cs.mpgc.rpg129380.model.registry.RegistroLivelli;
import it.unicam.cs.mpgc.rpg129380.model.registry.RegistroMostri;

public class Missione {

    private final Nemico mostro;
    private final int id;

    public Missione(int indice, Personaggio personaggio) {
        this.id = indice;
        LivelloDati livelloDati = RegistroLivelli.get().daIndice(indice);
        MostroDati mostroDati = RegistroMostri.get().daChiave(livelloDati.getChiaveMostro());
        this.mostro = new Nemico(mostroDati);
    }

    public Nemico getMostro(){
        return mostro;
    }

    public boolean isCompletata(){
        return mostro.sconfitto();
    }

    public int getId(){
        return id;
    }
}