package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import it.unicam.cs.mpgc.rpg129380.interfaces.EffettoOggetto;

public final class FabbricaEffetti {

    private FabbricaEffetti() {}

    public static EffettoOggetto crea(String tipoEffetto, int parametro) {
        return switch (tipoEffetto) {

            case "CURA_VITA"     -> (p, n) -> p.cura(parametro);

            case "DANNO_NEMICO"  -> (p, n) -> n.subisciDanno(parametro);

            case "BONUS_ATTACCO" -> (p, n) -> p.aggiungiBonusAttacco(parametro);

            case "BONUS_DIFESA"  -> (p, n) -> p.aggiungiBonusDifesa(parametro);

            case "GIGA_POZIONE"  -> (p, n) -> {
                p.aggiungiBonusDifesa(parametro);
                p.aggiungiBonusAttacco(parametro);
                p.cura(p.getVitaMax());
            };

            case "SFERA_RIVELATRICE" -> (p, n) -> {
                p.aggiungiBonusAttacco(parametro);
                p.subisciDanno(20);
            };

            default -> throw new IllegalArgumentException(
                    "Tipo effetto sconosciuto: '" + tipoEffetto + "'. "
            );
        };
    }
}