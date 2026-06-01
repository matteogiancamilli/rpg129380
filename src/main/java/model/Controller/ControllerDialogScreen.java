package model.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Personaggio;
import model.Livello;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

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
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(currentPersonaggio);

            try {
                String projectRoot = System.getProperty("user.dir");
                File saveFile = new File(projectRoot, "savegame.json");
                
                try (FileWriter writer = new FileWriter(saveFile)) {
                    writer.write(json);
                }
            } catch (IOException e) {
                System.err.println("Errore: " + e.getMessage());
            }
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}