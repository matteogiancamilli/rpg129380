package it.unicam.cs.mpgc.rpg129380.controller;

import it.unicam.cs.mpgc.rpg129380.interfaces.Inizializzabile;
import it.unicam.cs.mpgc.rpg129380.model.NavigatoreSchermate;
import it.unicam.cs.mpgc.rpg129380.model.gioco.GestoreCombattimento;
import it.unicam.cs.mpgc.rpg129380.model.gioco.Livello;
import it.unicam.cs.mpgc.rpg129380.model.gioco.Missione;
import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.IOException;

public class ControllerDialogScreen implements Inizializzabile {

    @FXML
    private TextArea textArea;

    private Personaggio currentPersonaggio;

    private GestoreSalvataggi gestoreSalvataggi;


    public ControllerDialogScreen() {}


    public void setGestoreSalvataggi(GestoreSalvataggi gestore) {
        this.gestoreSalvataggi = gestore;
    }

    @FXML
    public void initialize() {
    }

    public void setStoria(String text) {
        if (textArea != null) {
            textArea.setText(text);
        }
    }

    public void initData(Personaggio personaggio) {
        if (personaggio != null) {
            this.currentPersonaggio = personaggio;
            int livelloCorrente = personaggio.getLivello();
            if (livelloCorrente > 0 && livelloCorrente <= Livello.values().length) {
                String storiaLivello = Livello.values()[livelloCorrente - 1].getIntroduzione();
                setStoria(storiaLivello);
            }
        }
    }

    public void initDati(Personaggio personaggio, GestoreSalvataggi gestoreSalvataggi) {
        this.setGestoreSalvataggi(gestoreSalvataggi);
        this.initData(personaggio);
    }

    @FXML
    private void handleContinua(ActionEvent event) {
        // 1. Salva la partita prima di procedere
        if (currentPersonaggio != null) {
            try {
                gestoreSalvataggi.salva(currentPersonaggio);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setContentText("Impossibile salvare prima della battaglia!");
                alert.showAndWait();
                return; // Se fallisce il salvataggio, fermati
            }
        }

        // 2. Prepara la logica del combattimento
        Missione missione = new Missione(currentPersonaggio.getLivello() - 1, currentPersonaggio);
        GestoreCombattimento gestore = new GestoreCombattimento(currentPersonaggio, missione.getMostro());

        // 3. Carica il file FXML della battaglia e usa initData
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/battaglia.fxml"));
            javafx.scene.Parent root = loader.load(); // Carica l'interfaccia grafica

            // Recupera il controller associato al file appena caricato
            ControllerBattaglia cb = loader.getController();

            // ECCO DOVE USI INITDATA: Passi il gestore al nuovo controller
            // affinché possa aggiornare le barre HP e i nomi prima che la scena appaia
            cb.initData(gestore);
            cb.setNavigatore(new NavigatoreSchermate(this.gestoreSalvataggi));

            // 4. Cambia la scena nello Stage attuale senza aprire mille finestre
            Stage stageCorrente = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stageCorrente.setScene(new javafx.scene.Scene(root, 700, 720));
            stageCorrente.setTitle("Battaglia");
            stageCorrente.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di caricamento");
            alert.setContentText("Impossibile caricare la schermata di battaglia.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleSaveAndExit(ActionEvent event) {
        if (currentPersonaggio != null) {
            try {
                gestoreSalvataggi.salva(currentPersonaggio);
            } catch (IOException e) {
                // MOSTRIAMO UN ALERT invece di printStackTrace
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di Salvataggio");
                alert.setContentText("Impossibile salvare la partita: " + e.getMessage());
                alert.showAndWait();
                return; // Interrompiamo l'uscita se fallisce il salvataggio
            }
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}