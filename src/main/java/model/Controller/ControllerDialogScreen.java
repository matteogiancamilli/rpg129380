package model.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.CreatoreSalvataggi;
import model.Personaggio;
import model.Livello;

public class ControllerDialogScreen {

    @FXML
    private TextArea textArea;

    @FXML
    private Button salvaEsci;

    private Personaggio currentPersonaggio;

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
    private void handleSaveAndExit(ActionEvent event) {
        if (currentPersonaggio != null) {
            CreatoreSalvataggi gestore = new CreatoreSalvataggi();
            gestore.salva(currentPersonaggio);  // ✅ usa il gestore corretto
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}