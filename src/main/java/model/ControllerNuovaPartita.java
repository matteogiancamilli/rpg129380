package model;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class ControllerNuovaPartita {

    @FXML
    private ChoiceBox<String> selezionaClasse;
    @FXML
    private TextArea descrizioneClasse;

    @FXML
    public void initialize() {
        for (TipoClasse c : TipoClasse.values()) {
            selezionaClasse.getItems().add(c.getNome());
        }

        selezionaClasse.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {aggiornaDescrizione(newVal);
        });
    }

    private void aggiornaDescrizione(String nomeClasse) {
        for (TipoClasse c : TipoClasse.values()) {
            if (c.getNome().equals(nomeClasse)) {
                descrizioneClasse.setText(c.getDescrizione());
                break;
            }
        }
    }
}
