package model;

public class Missione {

    private Mostro mostro;
    private int id;
    private Personaggio personaggio; // Add reference to Personaggio

    public Missione(int livello, Personaggio personaggio) {
        this.id = livello;
        this.personaggio = personaggio;
        // Correctly assign the monster based on the level (id)
        if (livello >= 0 && livello < Mostro.values().length) {
            this.mostro = Mostro.values()[livello];
            this.mostro.resetMostro(); // Ensure monster starts with full health
        } else {
            // Handle case where level is out of bounds for available monsters
            System.err.println("Livello missione non valido: " + livello);
            this.mostro = Mostro.GOBLIN; // Default to Goblin or handle error appropriately
            this.mostro.resetMostro();
        }
    }

    public Mostro getMostro() {
        return mostro;
    }

    public boolean completaMissione() {
        // This method might be used for checking if the mission is completed
        // without necessarily simulating combat here.
        return mostro != null && mostro.sconfitto();
    }

    public boolean svolgiMissione() {
        if (mostro == null) {
            System.err.println("Nessun mostro assegnato alla missione.");
            return false;
        }

        // --- Basic Combat Simulation Placeholder ---
        // For now, let's assume a simple turn-based combat where the player attacks
        // until the monster is defeated. In a real game, this would be more complex.
        System.out.println("Inizia la missione: " + mostro.getNome());
        System.out.println(mostro.getIntroduzione());

        while (!mostro.sconfitto()) {
            // Player attacks monster
            int playerDamage = personaggio.getAttacco(); // Assuming Personaggio has getAttacco()
            mostro.subisciDanno(playerDamage);
            System.out.println(personaggio.getNome() + " attacca " + mostro.getNome() + " per " + playerDamage + " danni.");
            System.out.println(mostro.getNome() + " vita rimanente: " + mostro.getVita());

            if (mostro.sconfitto()) {
                System.out.println(mostro.getNome() + " è stato sconfitto!");
                personaggio.aumentaLivello(); // Increment player's level
                return true; // Mission completed
            }

            // Monster attacks player (simplified, no player health tracking here yet)
            // int monsterDamage = mostro.getAttacco();
            // personaggio.subisciDanno(monsterDamage); // Assuming Personaggio has subisciDanno()
            // System.out.println(mostro.getNome() + " attacca " + personaggio.getNome() + " per " + monsterDamage + " danni.");
            // if (personaggio.sconfitto()) {
            //     System.out.println(personaggio.getNome() + " è stato sconfitto! Game Over.");
            //     return false; // Mission failed
            // }

            // In a real game, you'd have more sophisticated turn management,
            // UI updates, and player choices.
            try {
                Thread.sleep(500); // Simulate some time passing per turn
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false; // Should not reach here if loop exits due to monster defeat
    }

    public boolean eseguiTurno(){
        // This method might be used for a more granular turn-based system
        // For now, svolgiMissione handles the full combat.
        return true;
    }
}