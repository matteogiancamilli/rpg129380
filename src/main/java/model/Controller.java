package model;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;

public class Controller {

    private final GestoreSalvataggi creatoreSalvataggi = new CreatoreSalvataggi();
    private final CreatorePersonaggi creatorePersonaggio = new CreatorePersonaggi();

    @FXML private Button bottoneNuovaPartita;
    @FXML private Button bottoneContinua;
    @FXML private Button bottoneEsci;
    @FXML private Button bottoneCrediti;

    /** Disabilita "Continua Partita" se non esiste alcun salvataggio. */
    @FXML
    private void initialize() {
        if (bottoneContinua != null) {
            bottoneContinua.setDisable(!creatoreSalvataggi.esisteSalvataggio());
        }
    }

    @FXML
    private void onNuovaPartita() {
        try {
            Personaggio personaggio = creatorePersonaggio.creazionePersonaggio();
            creatoreSalvataggi.nuovo(personaggio);
            new Gioco(personaggio, creatoreSalvataggi).avvia();
        } catch (IOException e) {
            mostraErrore("Errore durante l'avvio della nuova partita: " + e.getMessage());
        }
    }

    @FXML
    private void onContinuaPartita() {
        try {
            if (!creatoreSalvataggi.esisteSalvataggio()) {
                mostraInfo("Nessun salvataggio trovato. Inizia una nuova partita.");
                return;
            }
            Personaggio personaggio = creatoreSalvataggi.carica();
            if (personaggio == null) {
                mostraInfo("Salvataggio corrotto. Inizia una nuova partita.");
                return;
            }
            mostraInfo("Partita caricata correttamente!");
            new Gioco(personaggio, creatoreSalvataggi).avvia();
        } catch (IOException e) {
            mostraErrore("Errore durante il caricamento della partita: " + e.getMessage());
        }
    }

    @FXML
    private void onEsci() {
        javafx.application.Platform.exit();
    }

    @FXML
    private void onCrediti() {
        mostraInfo("Magic Strike\nSviluppato da Matteo Giancamilli");
    }

    private void mostraInfo(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
