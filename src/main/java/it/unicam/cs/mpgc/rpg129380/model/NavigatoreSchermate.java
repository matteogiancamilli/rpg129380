package it.unicam.cs.mpgc.rpg129380.model;

import it.unicam.cs.mpgc.rpg129380.controller.ControllerDialogScreen;
import it.unicam.cs.mpgc.rpg129380.model.gioco.Livello;
import it.unicam.cs.mpgc.rpg129380.interfaces.GestoreSalvataggi;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import it.unicam.cs.mpgc.rpg129380.interfaces.Inizializzabile;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

import java.io.IOException;

public class NavigatoreSchermate {

    private final GestoreSalvataggi gestoreSalvataggi;

    public NavigatoreSchermate(GestoreSalvataggi gestoreSalvataggi){
        this.gestoreSalvataggi = gestoreSalvataggi;
    }

    public void navigaASchermataSuccessiva(Stage stage, Personaggio p) {
        int prossimoLivello = p.getLivello();

        if (prossimoLivello <= Livello.values().length) {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/javafx/dialogscreen.fxml"));
                javafx.scene.Parent root = loader.load();

                ControllerDialogScreen controller = loader.getController();
                controller.initData(p);
                controller.setGestoreSalvataggi(this.gestoreSalvataggi);

                stage.setScene(new javafx.scene.Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                mostraPopup(Alert.AlertType.ERROR, "Errore", "Impossibile caricare la schermata successiva.");
            }
        } else {
            mostraPopup(Alert.AlertType.INFORMATION, "Vittoria!", "Hai completato il it.unicam.cs.mpgc.rpg129380.model.gioco! Sei il campione!");
        }
    }

    public void caricaSalvataggioENaviga(Stage stage) {
        try {
            Personaggio pCaricato = this.gestoreSalvataggi.carica();

            if (pCaricato == null) {
                mostraPopup(Alert.AlertType.WARNING, "Attenzione", "Nessun salvataggio trovato.");
                return;
            }

            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/javafx/dialogscreen.fxml"));
            javafx.scene.Parent root = loader.load();

            ControllerDialogScreen controller = loader.getController();
            controller.initData(pCaricato);
            controller.setGestoreSalvataggi(this.gestoreSalvataggi);
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            mostraPopup(Alert.AlertType.ERROR, "Errore", "Errore nel caricare il salvataggio.");
        }
    }

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

        Object controller = loader.getController();
        if (controller instanceof Inizializzabile) {
            ((Inizializzabile) controller).initDati(p, salvataggi);
        }

        stage.setScene(new Scene(root));
        stage.show();
    }
}