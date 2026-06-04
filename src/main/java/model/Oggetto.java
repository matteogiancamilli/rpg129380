package model;

public enum Oggetto {
    POZIONESALVAVITA(1, "Ripristina 50 di vita", 1),
    POZIONEDELDRAGO(2, "Infligge 30 di danno", 2),
    SCUDODELPALADINO(3, "Aumenta del 30% la difesa massima", 4),
    SPADADELLAROCCIA(4, "Aumenta del 30% l'attacco massimo", 3),
    GIGAPOZIONE(5, "Aumenta del 30% tutti i campi e li ripristina al massimo", 5 ),
    SFERARIVELATRICE(6, "Aumenta del 50% l'attacco massimo ma diminuisce di 20 la vita", 3);


    private final int id;
    private final String descrizione;
    private final int tipo;

    /*
    Tipi:
    - 1 = Cura Personale
    - 2 = Danno all'avversario
    - 3 = Aumento attacco
    - 4 = Aumento difesa
    - 5 = Oggetto Raro (La proabilità di ottenerlo è molto più bassa delle altre

     */

    Oggetto(int id, String descrizione, int tipo) {
        this.id = id;
        this.descrizione = descrizione;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getTipo() {
        return tipo;
    }

    public String getNomeVisuale() {
        return switch (this) {
            case POZIONESALVAVITA -> "Pozione Salva Vita";
            case POZIONEDELDRAGO  -> "Pozione del Drago";
            case SCUDODELPALADINO -> "Scudo del Paladino";
            case SPADADELLAROCCIA -> "Spada della Roccia";
            case GIGAPOZIONE      -> "Giga Pozione";
            case SFERARIVELATRICE -> "Sfera Rivelatrice";
        };
    }
}
