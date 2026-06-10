package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import it.unicam.cs.mpgc.rpg129380.model.registry.RegistroOggetti;

import java.util.List;
import java.util.Random;

public final class Drop {

    private static final Random RNG = new Random();
    private static final int PESO_NORMALE = 10;
    private static final int PESO_RARO    = 1;

    private Drop() {}

    public static OggettoDati dropCasuale() {
        List<OggettoDati> tutti = RegistroOggetti.get().tutti();
        int[] pesi = calcolaPesi(tutti);
        return estraiCasuale(tutti, pesi);
    }

    private static int[] calcolaPesi(List<OggettoDati> tutti) {
        int[] pesi = new int[tutti.size()];
        for (int i = 0; i < tutti.size(); i++) {
            pesi[i] = tutti.get(i).isRaro() ? PESO_RARO : PESO_NORMALE;
        }
        return pesi;
    }

    private static OggettoDati estraiCasuale(List<OggettoDati> tutti, int[] pesi) {
        int totale = 0;
        for (int peso : pesi) totale += peso;

        int dado = RNG.nextInt(totale);
        int accumulato = 0;
        for (int i = 0; i < tutti.size(); i++) {
            accumulato += pesi[i];
            if (dado < accumulato) return tutti.get(i);
        }
        return tutti.get(0);
    }
}