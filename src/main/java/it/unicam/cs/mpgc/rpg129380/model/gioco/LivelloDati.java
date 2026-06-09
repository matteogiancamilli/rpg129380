package it.unicam.cs.mpgc.rpg129380.model.gioco;

public class LivelloDati {

    private int id;
    private String chiaveMostro;
    private String introduzione;

    public LivelloDati() {}

    public LivelloDati(int id, String chiaveMostro, String introduzione) {
        this.id = id;
        this.chiaveMostro = chiaveMostro;
        this.introduzione = introduzione;
    }

    public int getId(){
        return id;
    }

    public String getChiaveMostro(){
        return chiaveMostro;
    }

    public String getIntroduzione(){
        return introduzione;
    }
}