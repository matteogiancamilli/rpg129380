package model;

public class Guerriero extends Classe{

    public Guerriero() {
        super("model.Guerriero", 300, 2, 1);
    }

    @Override
    public Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Fendente", "Il guerriero esegue un fendente con la sua spada",
                        20, 0, 10, 0,0),
                new Abilita("Liberazione", "Il guerriero si libera di peso, aumentando la sua velocità e ripristinando il mana",
                        0, 20 , 20, 1 , 20),
                new Abilita("Rinforzo", "Il guerriero ripristina parzialmente la sua vita", -20 , 0, 20, 2,0),
                new Abilita("Superspada", "Il guerriero sferra un potentissimo fendente",
                        40, 0, 40, 3,0)
                };
        }
    }
