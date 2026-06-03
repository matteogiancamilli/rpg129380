package model.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;

public class ControllerNuovaPartita {

    @FXML
    private ChoiceBox<TipoClasse> selezionaClasse;
    @FXML
    private TextArea descrizioneClasse;
    @FXML
    private Button iniziaPartita;
    @FXML
    private TextField nomePersonaggio;
    @FXML
    private TextArea errorDisplay;

    @FXML
    public void initialize() {
        selezionaClasse.getItems().addAll(TipoClasse.values());
        selezionaClasse.setConverter(new StringConverter<TipoClasse>() {
            public String toString(TipoClasse t) {
                return t.getNome(); }
            public TipoClasse fromString(String s) {
                return null; }
        });
        selezionaClasse.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if (newV != null) descrizioneClasse.setText(newV.getDescrizione());
                });
        if (!selezionaClasse.getItems().isEmpty()) {
            selezionaClasse.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void iniziaPartita(javafx.event.ActionEvent actionEvent) throws IOException {
        if (nomePersonaggio.getText().isEmpty()) {
            errorDisplay(1);
            return;
        }
        if (selezionaClasse.getValue() == null) {
            errorDisplay(2);
            return;
        }
        TipoClasse classe = selezionaClasse.getValue();
        Personaggio p = new Personaggio(nomePersonaggio.getText(), classe.getVita(), classe.getMana(), 1,new Inventario(null), classe, classe.abilitaIniziali());
        new CreatoreSalvataggi().nuovo(p);
        cambiaScena(actionEvent, "/javafx/dialogscreen.fxml", p);
    }

    private void cambiaScena(javafx.event.ActionEvent event, String fxml, Personaggio personaggio) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        ControllerDialogScreen dialogController = loader.getController();
        if (dialogController != null) {
            dialogController.initData(personaggio);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void errorDisplay(int errore){
        switch (errore){
            case 1:
                errorDisplay.setText("Inserisci un nome per il personaggio");
                break;
            case 2:
                errorDisplay.setText("Seleziona una classe");

        }
    }
}