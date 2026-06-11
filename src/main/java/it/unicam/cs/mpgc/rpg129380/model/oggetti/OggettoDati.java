package it.unicam.cs.mpgc.rpg129380.model.oggetti;

import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

public abstract class OggettoDati {

    private String chiave;
    private String nomeVisuale;
    private String descrizione;
    private String tipoOggetto;
    private boolean raro;

    public OggettoDati() {}

    public abstract void applicaEffetto(Personaggio p, Nemico n);

    public TipoOggetto getTipoOggettoEnum() {
        return TipoOggetto.valueOf(tipoOggetto);
    }

    public String getChiave(){
        return chiave;
    }

    public String getNomeVisuale(){
        return nomeVisuale;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public String getTipoOggetto(){
        return tipoOggetto;
    }

    public boolean isRaro(){
        return raro;
    }
}