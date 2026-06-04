package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import java.util.Random;

public class Drop {
    private static final Random RNG = new Random();
    private static final int PESO_NORMALE = 10;
    private static final int PESO_RARO = 1;

    public static Oggetto dropCasuale() {
        Oggetto[] tutti = Oggetto.values();
        int[] pesi = calcolaPesi(tutti);
        return estraiCasuale(tutti, pesi);
    }

    private static int[] calcolaPesi(Oggetto[] tutti) {
        int[] pesi = new int[tutti.length];

        for (int i = 0; i < tutti.length; i++) {
            pesi[i] = (tutti[i].getTipo() == TipoOggetto.RARO) ? PESO_RARO : PESO_NORMALE;
        }

        return pesi;
    }

    private static Oggetto estraiCasuale(Oggetto[] tutti, int[] pesi) {
        int totale = 0;
        for (int peso : pesi) {
            totale += peso;
        }

        int dado = RNG.nextInt(totale);
        int accumulato = 0;

        for (int i = 0; i < tutti.length; i++) {
            accumulato += pesi[i];
            if (dado < accumulato) return tutti[i];
        }

        return tutti[0];
    }
}