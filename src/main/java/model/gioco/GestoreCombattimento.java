package model.gioco;

import model.nemici.Nemico;
import model.personaggio.Abilita;
import model.personaggio.Oggetto;
import model.personaggio.Personaggio;

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

    /** Danno effettivo inflitto al mostro nell'ultimo turno (per il log). */
    private int ultimoDannoInflitto = 0;
    /** Danno effettivo subito dal mostro nell'ultimo turno (per il log). */
    private int ultimoDannoSubito   = 0;

    public GestoreCombattimento(Personaggio personaggio, Nemico mostro) {
        this.personaggio = personaggio;
        this.mostro      = mostro;
    }

    // ────────────────────────────────────────────────────────────────────────
    // Turno normale: abilità del giocatore → contrattacco mostro → reset bonus
    // ────────────────────────────────────────────────────────────────────────
    public RisultatoTurno eseguiTurno(Abilita abilita) {
        if (!abilita.abilitaPronta())
            return RisultatoTurno.ABILITA_IN_COOLDOWN;
        if (!personaggio.usaMana(abilita.getCostoMana()))
            return RisultatoTurno.MANA_INSUFFICIENTE;

        abilita.attivaAbilita();

        // Attacco / cura del giocatore
        if (abilita.isCura()) {
            personaggio.cura(-abilita.getDanno());
            ultimoDannoInflitto = 0;
        } else if (abilita.getDanno() > 0) {
            // Applica bonus attacco % se attivo
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

        // Contrattacco del mostro con moltiplicatore casuale
        int dannoMostro = mostro.getTipo().getAttaccoEffettivo();
        ultimoDannoSubito = dannoMostro;
        personaggio.subisciDanno(dannoMostro);   // subisciDanno applica già il bonus difesa

        // Fine turno: reset bonus oggetti e tick cooldown
        personaggio.eseguiFineTurno(); // <-- Feature Envy risolta

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

    // ────────────────────────────────────────────────────────────────────────
    // Uso oggetto: effetto immediato, nessun contrattacco, turno resta al
    // giocatore. I bonus % rimangono attivi fino alla fine del prossimo turno.
    // ────────────────────────────────────────────────────────────────────────
    public boolean eseguiUsoOggetto(Oggetto oggetto) {
        oggetto.applicaEffetto(personaggio, mostro);
        return personaggio.getInventario().rimuovi(oggetto);
    }

    // ── Accessori per il log ──────────────────────────────
    public int getUltimoDannoInflitto() { return ultimoDannoInflitto; }
    public int getUltimoDannoSubito()   { return ultimoDannoSubito;   }

    public Personaggio getPersonaggio() { return personaggio; }
    public Nemico      getMostro()      { return mostro; }
}


//TODO
/*
Impostare descrizioni classi
Finire GUI combattimento CSS
Migliorare descrizioni oggetti (Mettere le percentuali)
Controllare SOLID e Code Smells
Rimuovere Commenti e Beautify
Fare Wiki e README
 */
