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
        textArea.setEditable(false);
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
        if (currentPersonaggio != null) {
            try {
                gestoreSalvataggi.salva(currentPersonaggio);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setContentText("Impossibile salvare prima della battaglia!");
                alert.showAndWait();
                return;
            }
        }

        Missione missione = new Missione(currentPersonaggio.getLivello() - 1, currentPersonaggio);
        GestoreCombattimento gestore = new GestoreCombattimento(currentPersonaggio, missione.getMostro());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/battaglia.fxml"));
            javafx.scene.Parent root = loader.load();

            ControllerBattaglia cb = loader.getController();

            cb.initData(gestore);
            cb.setNavigatore(new NavigatoreSchermate(this.gestoreSalvataggi));

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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di Salvataggio");
                alert.setContentText("Impossibile salvare la partita: " + e.getMessage());
                alert.showAndWait();
                return;
            }
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}