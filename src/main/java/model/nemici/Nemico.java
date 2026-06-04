package model.nemici;

public class Nemico {
    private final Mostro tipo;
    private int vitaCorrente;

    public Nemico(Mostro tipo){
        this.tipo = tipo;
        this.vitaCorrente = tipo.getVitaMassima();
    }

    public Mostro getTipo(){
        return tipo;
    }

    public int getVitaCorrente(){
        return vitaCorrente;
    }

    public void subisciDanno(int danno){
        this.vitaCorrente -= danno;
        if (this.vitaCorrente < 0) {
            this.vitaCorrente = 0;
        }
    }

    public boolean sconfitto(){
        return vitaCorrente <= 0;
    }

    public void resetMostro(){
        this.vitaCorrente = tipo.getVitaMassima();
    }
}