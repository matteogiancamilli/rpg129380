package model;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Controller.ControllerDialogScreen;
import java.io.IOException;

public class NavigatoreSchermate {

    private final GestoreSalvataggi gestoreSalvataggi;

    public NavigatoreSchermate(GestoreSalvataggi gestoreSalvataggi){
        this.gestoreSalvataggi = gestoreSalvataggi;
    }

    public void navigaASchermataSuccessiva(Stage stage, Personaggio p) {
        int prossimoLivello = p.getLivello();

        // Controllo vittoria
        if (prossimoLivello <= Livello.values().length) {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                        getClass().getResource("/javafx/dialogscreen.fxml"));
                javafx.scene.Parent root = loader.load();

                ControllerDialogScreen controller = loader.getController();
                controller.initData(p);

                // Usa lo stage passato come parametro, non cercare di prenderlo dal battleLog!
                stage.setScene(new javafx.scene.Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                mostraPopup(Alert.AlertType.ERROR, "Errore", "Impossibile caricare la schermata successiva.");
            }
        } else {
            // Invece di scrivere sul log della vecchia battaglia, mostriamo un popup di vittoria
            mostraPopup(Alert.AlertType.INFORMATION, "Vittoria!", "Hai completato il gioco! Sei il campione!");
        }
    }

    public void caricaSalvataggioENaviga(Stage stage) {
        try {
            Personaggio pCaricato = new CreatoreSalvataggi().carica();

            if (pCaricato == null) {
                mostraPopup(Alert.AlertType.WARNING, "Attenzione", "Nessun salvataggio trovato.");
                return;
            }

            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/javafx/dialogscreen.fxml"));
            javafx.scene.Parent root = loader.load();

            ControllerDialogScreen controller = loader.getController();
            controller.initData(pCaricato);

            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            mostraPopup(Alert.AlertType.ERROR, "Errore", "Errore nel caricare il salvataggio.");
        }
    }

    // Metodo helper per tenere la classe pulita
    private void mostraPopup(Alert.AlertType tipo, String titolo, String messaggio) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}