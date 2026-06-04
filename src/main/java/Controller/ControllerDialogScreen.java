package Controller;

import gioco.GestoreCombattimento;
import gioco.Livello;
import gioco.Missione;
import interfaces.PersistenzaSalvataggio;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.*;
import model.personaggio.Personaggio;

import java.io.IOException;

public class ControllerDialogScreen {

    @FXML
    private TextArea textArea;

    private Personaggio currentPersonaggio;
    private final PersistenzaSalvataggio salvataggi;

    public ControllerDialogScreen() {
        this.salvataggi = new CreatoreSalvataggi();
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

    @FXML
    private void handleContinua(ActionEvent event){
        try {
            salvataggi.salva(currentPersonaggio);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Missione missione = new Missione(currentPersonaggio.getLivello() - 1, currentPersonaggio);
        GestoreCombattimento gestore = new GestoreCombattimento(currentPersonaggio, missione.getMostro());
        ControllerBattaglia cb = new ControllerBattaglia();
        cb.setGestore(gestore);

        Stage stageCorrente = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stageCorrente.close();

        Stage battleStage = new Stage();
        battleStage.setScene(cb.costruisciScena());
        battleStage.setTitle("Battaglia");
        battleStage.setMinWidth(700);
        battleStage.setMinHeight(700);
        battleStage.show();
    }

    @FXML
    private void handleSaveAndExit(ActionEvent event) {
        if (currentPersonaggio != null) {
            CreatoreSalvataggi gestore = new CreatoreSalvataggi();
            try {
                gestore.salva(currentPersonaggio);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}