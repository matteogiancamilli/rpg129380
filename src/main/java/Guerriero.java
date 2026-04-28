import java.util.ArrayList;
import java.util.List;

public class Guerriero extends Classe{
    private Abilita[] abilitas = {"Fendente","Blocco Difensivo","Superspadata"};

    public Guerriero(String nome, int vita, int attacco, int velocita, Abilita[] abilitas) {
        super("Guerriero", 300, 200, 100);
        this.abilitas = abilitas;
    }

    @Override
    public Abilita[] abilitaIniziali() {
        return new Abilita[]{
                new Abilita("Fendente", "Il guerriero esegue un fendente con la sua spada", 20, 10, 0),
                new Abilita(),
                new Abilita()}
        }
    }
}
