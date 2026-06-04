package it.unicam.cs.mpgc.rpg129380.controller;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import it.unicam.cs.mpgc.rpg129380.model.salvataggi.CreatoreSalvataggi;
import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import it.unicam.cs.mpgc.rpg129380.model.NavigatoreSchermate;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.File;
import java.io.IOException;

public class ControllerStart {

    @FXML
    private Button bottoneNuovaPartita;
    @FXML
    private Button bottoneContinuaPartita;
    @FXML
    private Button bottoneEsci;
    @FXML
    private Button bottoneCrediti;

    private GestoreSalvataggi gestoreSalvataggi;

    public void setGestoreSalvataggi(GestoreSalvataggi gestore) {
        this.gestoreSalvataggi = gestore;
    }

    @FXML
    private void initialize(){
        this.gestoreSalvataggi = new CreatoreSalvataggi();
        boolean partitaEsistente = controllaSalvataggio();
        bottoneContinuaPartita.setDisable(!partitaEsistente);
    }

    @FXML
    private void esci(){
        System.exit(0);
    }

    private boolean controllaSalvataggio() {
        File file = new File("savegame.json");
        return file.exists();
    }

    @FXML
    private void apriNuovaPartita(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        NavigatoreSchermate.cambiaScena(stage, "/javafx/nuovapartita.fxml", null, this.gestoreSalvataggi);
    }

    @FXML
    private void continuaPartita(javafx.event.ActionEvent actionEvent) throws IOException {
        Personaggio personaggio = gestoreSalvataggi.carica();
        if (personaggio == null) {
            bottoneContinuaPartita.setDisable(true);
            return;
        }

        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        NavigatoreSchermate.cambiaScena(stage, "/javafx/dialogscreen.fxml", personaggio, this.gestoreSalvataggi);
    }

    @FXML
    private void crediti() {
        Stage finestraCrediti = new Stage();
        Label label = new Label("Sviluppato da: Matteo Giancamilli \nUniversità degli Studi di Camerino \nCorso di Informatica L-31 \nMatricola: 129380\nVersione: 1.0");
        StackPane layout = new StackPane(label);

        Scene scena = new Scene(layout, 300, 200);
        finestraCrediti.setScene(scena);
        finestraCrediti.initModality(Modality.APPLICATION_MODAL);
        finestraCrediti.setTitle("Crediti");
        finestraCrediti.showAndWait();
    }

}