package it.unicam.cs.mpgc.rpg129380;

import it.unicam.cs.mpgc.rpg129380.Controller.ControllerStart;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.unicam.cs.mpgc.rpg129380.model.salvataggi.CreatoreSalvataggi;

public class MainFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/startingscreen.fxml"));
        Parent root = loader.load();

        // Iniezione della dipendenza
        ControllerStart startController = loader.getController();
        startController.setGestoreSalvataggi(new CreatoreSalvataggi());

        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
