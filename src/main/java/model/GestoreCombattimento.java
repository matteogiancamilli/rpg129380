package model;

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

    public GestoreCombattimento(Personaggio personaggio, Nemico mostro){
        this.personaggio = personaggio;
        this.mostro = mostro;
    }

    /**
     * Turno completo: il giocatore usa un'abilità, poi il mostro risponde.
     */
    public RisultatoTurno eseguiTurno(Abilita abilita){
        if (!abilita.abilitaPronta()) {
            return RisultatoTurno.ABILITA_IN_COOLDOWN;
        }
        if (!personaggio.usaMana(abilita.getCostoMana())){
            return RisultatoTurno.MANA_INSUFFICIENTE;
        }

        abilita.attivaAbilita();

        if (abilita.isCura()){
            personaggio.cura(-abilita.getDanno());
        } else if (abilita.getDanno() > 0) {
            mostro.subisciDanno(abilita.getDanno());
        }

        if (abilita.getRipristinoMana() > 0){
            personaggio.ripristinaMana(abilita.getRipristinoMana());
        }

        if (mostro.sconfitto()){
            personaggio.aumentaLivello();
            return RisultatoTurno.MOSTRO_SCONFITTO;
        }

        int dannoMostro = mostro.getTipo().getAttacco();
        personaggio.subisciDanno(dannoMostro);

        for (Abilita a : personaggio.getAbilitas()){
            a.tickCooldown();
        }

        if (personaggio.isSconfitto()){
            return RisultatoTurno.PERSONAGGIO_SCONFITTO;
        }

        boolean tutteBloccate = true;
        for (Abilita a : personaggio.getAbilitas()){
            if (a.abilitaPronta() && personaggio.getMana() >= a.getCostoMana()){
                tutteBloccate = false;
                break;
            }
        }
        if (tutteBloccate && !mostro.sconfitto()){
            return RisultatoTurno.MANA_ESAURITO;
        }

        return RisultatoTurno.OK;
    }

    public boolean eseguiUsoOggetto(Oggetto oggetto) {
        switch (oggetto) {
            case POZIONESALVAVITA -> personaggio.cura(50);
            case POZIONEDELDRAGO  -> mostro.subisciDanno(30);
            case SCUDODELPALADINO -> personaggio.aumentaDifesa(30);
            case SPADADELLAROCCIA -> personaggio.aumentaAttacco(30);
            case GIGAPOZIONE -> {
                personaggio.aumentaVitaMax(30);
                personaggio.aumentaManaMax(30);
                personaggio.cura(personaggio.getVitaMax());
                personaggio.ripristinaMana(personaggio.getManaMax());
            }
            case SFERARIVELATRICE -> {
                personaggio.aumentaAttacco(50);
                personaggio.subisciDanno(20);
            }
        }
        // Rimuove dall'inventario
        return personaggio.getInventario().rimuovi(oggetto);
    }

    public Personaggio getPersonaggio(){
        return personaggio;
    }

    public Nemico getMostro(){
        return mostro;
    }
}