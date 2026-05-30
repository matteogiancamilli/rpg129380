package model;
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

import java.awt.event.ActionEvent;
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

    @FXML
    private void initialize(){
       boolean partitaEsistente = controllaSalvataggio();
       if(partitaEsistente) bottoneContinuaPartita.setVisible(true);
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
    private void setBottoneNuovaPartita(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nuovapartita.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void crediti() {
        Stage finestraCrediti = new Stage();

        Label label = new Label("Sviluppato da: Tuo Nome\nVersione: 1.0");
        StackPane layout = new StackPane(label);

        Scene scena = new Scene(layout, 300, 200);
        finestraCrediti.setScene(scena);

        finestraCrediti.initModality(Modality.APPLICATION_MODAL);
        finestraCrediti.setTitle("Crediti");

        finestraCrediti.showAndWait();
    }

}
