package it.unicam.cs.mpgc.rpg129380.model.personaggio;

import it.unicam.cs.mpgc.rpg129380.interfaces.EffettoOggetto;
import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;

public class OggettoDati {

    private String chiave;
    private String nomeVisuale;
    private String descrizione;
    private String tipoOggetto;
    private String tipoEffetto;
    private int parametro;
    private boolean raro;

    private transient EffettoOggetto effetto;

    public OggettoDati() {}

    public void applicaEffetto(Personaggio p, Nemico n) {
        if (effetto == null) {
            effetto = FabbricaEffetti.crea(tipoEffetto, parametro);
        }
        effetto.applica(p, n);
    }

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

    public String getTipoEffetto(){
        return tipoEffetto;
    }

    public int getParametro(){
        return parametro;
    }

    public boolean isRaro(){
        return raro;
    }
}