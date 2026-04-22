import java.util.ArrayList;
import java.util.List;

public class Guerriero extends Classe{
    private Abilita[] abilita = {"Fendente","Blocco Difensivo","Superspadata"};

    public Guerriero(String nome, int vita, int attacco, int velocita, Abilita[] abilitas) {
        super("Guerriero", 300, 200, 100, abilitas);
    }
}
