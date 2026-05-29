package model;

public class Missione {

    Mostro mostro;
    int id;

    public Missione(int livello) {
        this.id = livello;
    }

    public boolean completaMissione() {
        return true;
    }

    public boolean svolgiMissione(){
        mostro = mostro.values()[id];
        while(true){

            if(mostro.sconfitto()){
                return true;
            }


        }
    }

    public boolean eseguiTurno(){
        //TODO
        return true;
    }


}
