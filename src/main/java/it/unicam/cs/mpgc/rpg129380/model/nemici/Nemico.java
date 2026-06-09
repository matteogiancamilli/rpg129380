package it.unicam.cs.mpgc.rpg129380.model.nemici;

public class Nemico {

    private final MostroDati tipo;
    private int vitaCorrente;

    public Nemico(MostroDati tipo) {
        this.tipo = tipo;
        this.vitaCorrente = tipo.getVitaMassima();
    }

    public void subisciDanno(int danno) {
        this.vitaCorrente -= danno;
        if (this.vitaCorrente < 0) {
            this.vitaCorrente = 0;
        }
    }

    public boolean sconfitto() {
        return vitaCorrente <= 0;
    }

    public void resetMostro() {
        this.vitaCorrente = tipo.getVitaMassima();
    }

    public MostroDati getTipo(){
        return tipo;
    }

    public int getVitaCorrente(){
        return vitaCorrente;
    }
}