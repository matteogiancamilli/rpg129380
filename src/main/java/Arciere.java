public class Arciere extends Classe{

    public Arciere() {
        super("Guerriero", 100, 3, 3, null);
        this.abilita = abilitaIniziali();
    }

    @Override
    public Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Scoccata", "L'arciere lancia una precisa freccia",
                        30, 0, 10, 0,0),
                new Abilita("Silenzio", "L'arciere si nasconde, ripristinano il suo mana",
                        0, 20 , 20, 1,30 ),
                new Abilita("Cura Veloce", "L'arciere recupera un po' di vita", -10 , 0, 10, 1,0),
                new Abilita("Megafreccia", "L'arciere lancia una megafreccia ad alto danno",
                        60, 0, 60, 2, 0)
        };
    }
}
