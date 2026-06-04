package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CreatoreSalvataggi;
import interfaces.GestoreSalvataggi;
import model.NavigatoreSchermate;
import model.personaggio.Personaggio;

import java.io.File;
import java.io.IOException;

import static model.NavigatoreSchermate.cambiaScena;

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
        // 1. Recuperiamo la finestra (Stage) dal bottone che è stato premuto
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

        // 2. Usiamo il navigatore (Passiamo null per Personaggio perché stiamo solo aprendo il form)
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
        // Qui passiamo il personaggio caricato
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