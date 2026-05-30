import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/startingscreen.fxml"));
        } catch (Exception e) {
            System.err.println("Errore: impossibile caricare il file FXML");
            e.printStackTrace();
        }
        stage.setTitle("Magic Strike");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
