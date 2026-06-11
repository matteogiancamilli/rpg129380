package it.unicam.cs.mpgc.rpg129380.model.oggetti;

//Tipi di Oggetti possibili
public enum TipoOggetto {
    CURA    ("btn-oggetto-cura"),
    DANNO   ("btn-oggetto-danno"),
    ATTACCO ("btn-oggetto-attacco"),
    DIFESA  ("btn-oggetto-difesa"),
    RARO    ("btn-oggetto-raro");

    private final String classeCSS;

    TipoOggetto(String classeCSS) {
        this.classeCSS = classeCSS;
    }

    public String getClasseCSS()  {
        return classeCSS;
    }
}
