package it.unicam.cs.mpgc.rpg129380.controller;

import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import it.unicam.cs.mpgc.rpg129380.interfaces.Inizializzabile;
import it.unicam.cs.mpgc.rpg129380.model.classi.ClasseDati;
import it.unicam.cs.mpgc.rpg129380.model.registry.RegistroClassi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import it.unicam.cs.mpgc.rpg129380.model.NavigatoreSchermate;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Inventario;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.IOException;

public class ControllerNuovaPartita implements Inizializzabile {

    @FXML private ChoiceBox<ClasseDati> selezionaClasse;
    @FXML private TextArea              descrizioneClasse;
    @FXML private Button                iniziaPartita;
    @FXML private TextField             nomePersonaggio;
    @FXML private TextArea              errorDisplay;

    private GestoreSalvataggi gestoreSalvataggi;

    @FXML
    public void initialize() {
        descrizioneClasse.setEditable(false);
        errorDisplay.setEditable(false);

        selezionaClasse.getItems().addAll(RegistroClassi.get().tutte());

        selezionaClasse.setConverter(new StringConverter<ClasseDati>() {
            @Override public String toString(ClasseDati c)       { return c != null ? c.getNome() : ""; }
            @Override public ClasseDati fromString(String s)     { return null; }
        });

        selezionaClasse.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if (newV != null) descrizioneClasse.setText(newV.getDescrizione());
                }
        );

        if (!selezionaClasse.getItems().isEmpty()) {
            selezionaClasse.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void iniziaPartita(javafx.event.ActionEvent actionEvent) throws IOException {
        if (nomePersonaggio.getText().isBlank()) {
            mostraErrore("Inserisci un nome per il personaggio");
            return;
        }
        if (selezionaClasse.getValue() == null) {
            mostraErrore("Seleziona una classe");
            return;
        }

        ClasseDati classe = selezionaClasse.getValue();
        Personaggio p = new Personaggio(
                nomePersonaggio.getText(),
                classe.getVita(),
                classe.getMana(),
                1,
                new Inventario(),
                classe,
                classe.abilitaIniziali()
        );
        this.gestoreSalvataggi.nuovo(p);

        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        NavigatoreSchermate.cambiaScena(stage, "/javafx/dialogscreen.fxml", p, this.gestoreSalvataggi);
    }

    @Override
    public void initDati(Personaggio personaggio, GestoreSalvataggi gestoreSalvataggi) {
        this.gestoreSalvataggi = gestoreSalvataggi;
    }

    @FXML
    private void mostraErrore(String messaggio) {
        errorDisplay.setText(messaggio);
    }
}