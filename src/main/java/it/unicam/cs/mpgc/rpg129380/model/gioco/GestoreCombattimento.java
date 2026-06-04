package it.unicam.cs.mpgc.rpg129380.model.gioco;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Oggetto;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

public class GestoreCombattimento {

    public enum RisultatoTurno {
        OK,
        MANA_INSUFFICIENTE,
        ABILITA_IN_COOLDOWN,
        MOSTRO_SCONFITTO,
        PERSONAGGIO_SCONFITTO,
        MANA_ESAURITO
    }

    private final Personaggio personaggio;
    private final Nemico mostro;
    private int ultimoDannoInflitto = 0;
    private int ultimoDannoSubito   = 0;

    public GestoreCombattimento(Personaggio personaggio, Nemico mostro) {
        this.personaggio = personaggio;
        this.mostro      = mostro;
    }

    public RisultatoTurno eseguiTurno(Abilita abilita) {
        if (!abilita.abilitaPronta())
            return RisultatoTurno.ABILITA_IN_COOLDOWN;
        if (!personaggio.usaMana(abilita.getCostoMana()))
            return RisultatoTurno.MANA_INSUFFICIENTE;

        abilita.attivaAbilita();

        if (abilita.isCura()) {
            personaggio.cura(-abilita.getDanno());
            ultimoDannoInflitto = 0;
        } else if (abilita.getDanno() > 0) {
            int dannoEffettivo = personaggio.calcolaDannoEffettivo(abilita.getDanno());
            ultimoDannoInflitto = dannoEffettivo;
            mostro.subisciDanno(dannoEffettivo);
        }

        if (abilita.getRipristinoMana() > 0)
            personaggio.ripristinaMana(abilita.getRipristinoMana());

        if (mostro.sconfitto()) {
            personaggio.resettaBonus();
            personaggio.aumentaLivello();
            return RisultatoTurno.MOSTRO_SCONFITTO;
        }

        int dannoMostro = mostro.getTipo().getAttaccoEffettivo();
        ultimoDannoSubito = dannoMostro;
        personaggio.subisciDanno(dannoMostro);

        personaggio.eseguiFineTurno();

        if (personaggio.isSconfitto())
            return RisultatoTurno.PERSONAGGIO_SCONFITTO;

        boolean tutteBloccate = true;
        for (Abilita a : personaggio.getAbilitas()) {
            if (a.abilitaPronta() && personaggio.getMana() >= a.getCostoMana()) {
                tutteBloccate = false;
                break;
            }
        }
        if (tutteBloccate)
            return RisultatoTurno.MANA_ESAURITO;

        return RisultatoTurno.OK;
    }


    public boolean eseguiUsoOggetto(Oggetto oggetto) {
        oggetto.applicaEffetto(personaggio, mostro);
        return personaggio.getInventario().rimuovi(oggetto);
    }

    public int getUltimoDannoInflitto() {
        return ultimoDannoInflitto;
    }

    public int getUltimoDannoSubito()   {
        return ultimoDannoSubito;
    }

    public Personaggio getPersonaggio(){
        return personaggio;
    }

    public Nemico getMostro(){
        return mostro;
    }
}
