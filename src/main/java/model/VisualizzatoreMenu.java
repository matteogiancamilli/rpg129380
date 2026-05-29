package model;

public class VisualizzatoreMenu {

    public void visualizzaMenuIniziale() {
        System.out.println("Benvenuto in ...");
        System.out.println("Menu di gioco (Scrivi il numero corrispondente e premi invio per accedere alla sezione): ");
        System.out.println("1 - Nuova Partita");
        System.out.println("2 - Continua Partita");
        System.out.println("3 - Esci dal gioco");
    }

    public void stampaSceltaClassi(String nomePersonaggio){
        System.out.println("model.Personaggio creato: " + nomePersonaggio);
        System.out.println("Scegli la classe del personaggio: ");
        System.out.println("1 - model.Guerriero: Alta Vita/Moderato Attacco/Bassa Velocità");
        System.out.println("2 - model.Arciere: Bassa Vita/Alto Attacco/Alta Velocità");
        System.out.println("3 - model.Mago: Media Vita/Medio Attacco/Media Velocità");
    }
}
