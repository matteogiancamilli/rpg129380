package Controller;

import interfaces.GestoreSalvataggi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;
import model.classi.TipoClasse;
import model.personaggio.Inventario;
import model.personaggio.Personaggio;
import model.salvataggi.CreatoreSalvataggi;

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
            mostraErrore("Inserisci un nome per il personaggio");
            return;
        }
        if (selezionaClasse.getValue() == null) {
            mostraErrore("Seleziona una classe");
            return;
        }
        TipoClasse classe = selezionaClasse.getValue();
        Personaggio p = new Personaggio(nomePersonaggio.getText(), classe.getVita(), classe.getMana(), 1,new Inventario(null), classe, classe.abilitaIniziali());
        // 1. Istanziamo il gestore e salviamo la nuova partita
        GestoreSalvataggi salvataggi = new CreatoreSalvataggi();
        salvataggi.nuovo(p);

        // 2. Cambiamo scena andando al dialogo del Livello 1
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        NavigatoreSchermate.cambiaScena(stage, "/javafx/dialogscreen.fxml", p, salvataggi);
    }

    @FXML
    private void mostraErrore(String messaggio){
        errorDisplay.setText(messaggio);
    }
}