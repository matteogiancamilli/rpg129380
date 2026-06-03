package model;

public class GestoreCombattimento {

    public enum RisultatoTurno {
        OK,
        MANA_INSUFFICIENTE,
        ABILITA_IN_COOLDOWN,
        MOSTRO_SCONFITTO,
        PERSONAGGIO_SCONFITTO
    }

    private final Personaggio personaggio;
    private final Mostro mostro;

    public GestoreCombattimento(Personaggio personaggio, Mostro mostro) {
        this.personaggio = personaggio;
        this.mostro = mostro;
        this.mostro.resetMostro();
    }

    /**
     * Turno completo: il giocatore usa un'abilità, poi il mostro risponde.
     * Restituisce il risultato finale del turno.
     */
    public RisultatoTurno eseguiTurno(Abilita abilita) {

        // ── 1. Controlli pre-azione ───────────────────────
        if (!abilita.abilitaPronta()) {
            return RisultatoTurno.ABILITA_IN_COOLDOWN;
        }
        if (!personaggio.usaMana(abilita.getCostoMana())) {
            return RisultatoTurno.MANA_INSUFFICIENTE;
        }

        // ── 2. Applica l'abilità ──────────────────────────
        abilita.attivaAbilita();

        if (abilita.isCura()) {
            // danno negativo → cura il personaggio
            personaggio.cura(-abilita.getDanno());
        } else if (abilita.getDanno() > 0) {
            mostro.subisciDanno(abilita.getDanno());
        }

        // Ripristino mana (es. Liberazione, Silenzio, Meditazione)
        if (abilita.getRipristinoMana() > 0) {
            personaggio.ripristinaMana(abilita.getRipristinoMana());
        }

        // ── 3. Controlla se il mostro è sconfitto ─────────
        if (mostro.sconfitto()) {
            personaggio.aumentaLivello();
            return RisultatoTurno.MOSTRO_SCONFITTO;
        }

        // ── 4. Contrattacco del mostro ────────────────────
        personaggio.subisciDanno(mostro.getAttacco());

        // ── 5. Tick cooldown su tutte le abilità ──────────
        for (Abilita a : personaggio.getAbilitas()) {
            a.tickCooldown();
        }

        // ── 6. Controlla se il personaggio è sconfitto ────
        if (personaggio.isSconfitto()) {
            return RisultatoTurno.PERSONAGGIO_SCONFITTO;
        }

        return RisultatoTurno.OK;
    }

    public Personaggio getPersonaggio() { return personaggio; }
    public Mostro      getMostro()      { return mostro; }
}