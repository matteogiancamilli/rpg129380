package model;

import model.gioco.Livello;
import interfaces.GestoreSalvataggi;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Controller.ControllerDialogScreen;
import model.personaggio.Personaggio;
import model.salvataggi.CreatoreSalvataggi;

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
            mostraPopup(Alert.AlertType.INFORMATION, "Vittoria!", "Hai completato il model.gioco! Sei il campione!");
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

    public static void cambiaScena(Stage stage, String fxmlPath, Personaggio p, GestoreSalvataggi salvataggi) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigatoreSchermate.class.getResource(fxmlPath));
        Parent root = loader.load();

        // Gestione generica del passaggio di dati ai controller
        Object controller = loader.getController();
        if (controller instanceof ControllerDialogScreen) {
            ((ControllerDialogScreen) controller).initData(p);
            ((ControllerDialogScreen) controller).setGestoreSalvataggi(salvataggi);
        }
        // Aggiungere qui altri "if" per altri controller, oppure usare una interfaccia comune

        stage.setScene(new Scene(root));
        stage.show();
    }
}