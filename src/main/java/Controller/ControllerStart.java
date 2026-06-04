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
import model.personaggio.Personaggio;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/nuovapartita.fxml"));
        Parent root = loader.load();
        stageLoader(root, actionEvent);
    }

    @FXML
    private void continuaPartita(javafx.event.ActionEvent actionEvent) throws IOException {
        Personaggio personaggio = gestoreSalvataggi.carica();
        if (personaggio == null) {
            bottoneContinuaPartita.setDisable(true);
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/dialogscreen.fxml"));
        Parent root = loader.load();
        ControllerDialogScreen controllerDialogScreen = loader.getController();
        controllerDialogScreen.initData(personaggio);
        stageLoader(root, actionEvent);

    }

    private void stageLoader(Parent root, ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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