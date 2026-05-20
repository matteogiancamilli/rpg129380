public class Mago extends Classe{


    public Mago() {
        super("Mago", 200, 2, 2);
    }

    @Override
    public Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Palla di Fuoco", "Il mago lancia una palla di fuoco",
                        20, 0, 10, 0,0),
                new Abilita("Meditazione", "Il mago medita, ripristinando vita e mana",
                        -30, 0 , 0, 1,30 ),
                new Abilita("Cura", "Il mago ricarica la sua vita", -50 , 0, 30, 2,0),
                new Abilita("Stella Cadente", "Il Mago lancia una stella cadente",
                        60, 0, 40, 3, 0)
        };
    }
}
