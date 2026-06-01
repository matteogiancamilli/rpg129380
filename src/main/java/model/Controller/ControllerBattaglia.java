package model.Controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ControllerBattaglia extends Application {

    @Override
    public void start(Stage primaryStage) {

        // ==========================================
        // 1. ZONA COMBATTIMENTO (ARENA)
        // ==========================================

        // Personaggio (Sinistra)
        VBox playerBox = createCharacterBox("Eroe", "file:player.png", 1.0, "100/100 HP");

        // Nemico (Destra)
        VBox enemyBox = createCharacterBox("Goblin Oscuro", "file:enemy.png", 0.75, "75/100 HP");

        HBox arena = new HBox(100); // 100 pixel di spazio tra i due
        arena.setAlignment(Pos.CENTER);
        arena.setPadding(new Insets(20));
        arena.getChildren().addAll(playerBox, enemyBox);

        // ==========================================
        // 2. LOG DI BATTAGLIA
        // ==========================================

        Label battleLog = new Label("Un Goblin Oscuro ti sbarra la strada! Scegli la tua mossa.");
        battleLog.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #b22222;");
        battleLog.setPadding(new Insets(10));

        // ==========================================
        // 3. INTERFACCIA ABILITÀ E INVENTARIO
        // ==========================================

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // Impedisce di chiudere le tab

        // --- TAB ABILITÀ ---
        Tab abilitiesTab = new Tab("Abilità");
        HBox abilitiesLayout = new HBox(20);
        abilitiesLayout.setPadding(new Insets(10));

        ListView<String> abilitiesList = new ListView<>();
        abilitiesList.getItems().addAll("Fendente Magico", "Palla di Fuoco", "Cura Leggera");
        abilitiesList.setPrefSize(200, 150);

        Label abilityDesc = new Label("Seleziona un'abilità per vederne i dettagli.");
        abilityDesc.setWrapText(true);
        abilityDesc.setPrefWidth(300);

        // Listener per cambiare la descrizione quando si seleziona un'abilità
        abilitiesList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                switch (newSelection) {
                    case "Fendente Magico": abilityDesc.setText("Infligge danni fisici e magici al nemico. Costo: 10 MP."); break;
                    case "Palla di Fuoco": abilityDesc.setText("Lancia una sfera infuocata. Alta probabilità di scottare. Costo: 25 MP."); break;
                    case "Cura Leggera": abilityDesc.setText("Ripristina 30 HP al lanciatore. Costo: 15 MP."); break;
                }
            }
        });
        abilitiesLayout.getChildren().addAll(abilitiesList, abilityDesc);
        abilitiesTab.setContent(abilitiesLayout);

        // --- TAB INVENTARIO ---
        Tab inventoryTab = new Tab("Inventario");
        HBox inventoryLayout = new HBox(20);
        inventoryLayout.setPadding(new Insets(10));

        ListView<String> inventoryList = new ListView<>();
        inventoryList.getItems().addAll("Pozione Minore", "Elisir del Mana", "Bomba Fumogena");
        inventoryList.setPrefSize(200, 150);

        VBox itemActionBox = new VBox(10);
        Label itemDesc = new Label("Seleziona un oggetto.");
        itemDesc.setWrapText(true);
        itemDesc.setPrefWidth(300);

        Button useButton = new Button("Usa Oggetto");
        useButton.setDisable(true); // Disabilitato finché non si seleziona qualcosa

        // Listener per l'inventario
        inventoryList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                useButton.setDisable(false);
                switch (newSelection) {
                    case "Pozione Minore": itemDesc.setText("Cura 50 HP."); break;
                    case "Elisir del Mana": itemDesc.setText("Ripristina 40 MP."); break;
                    case "Bomba Fumogena": itemDesc.setText("Permette di fuggire dalla battaglia."); break;
                }
            }
        });

        // Azione del tasto "Usa"
        useButton.setOnAction(e -> {
            String selected = inventoryList.getSelectionModel().getSelectedItem();
            battleLog.setText("Hai usato: " + selected + "!");
        });

        itemActionBox.getChildren().addAll(itemDesc, useButton);
        inventoryLayout.getChildren().addAll(inventoryList, itemActionBox);
        inventoryTab.setContent(inventoryLayout);

        // Aggiungo le tab al TabPane
        tabPane.getTabs().addAll(abilitiesTab, inventoryTab);

        // ==========================================
        // 4. ASSEMBLAGGIO FINALE (BorderPane)
        // ==========================================

        VBox bottomSection = new VBox(10); // Contiene log e controlli
        bottomSection.getChildren().addAll(battleLog, tabPane);

        BorderPane root = new BorderPane();
        root.setCenter(arena);
        root.setBottom(bottomSection);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Magic Strike - Battaglia");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metodo helper per creare rapidamente i box dei personaggi
    private VBox createCharacterBox(String name, String imagePath, double healthProgress, String hpText) {
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // Se l'immagine non viene trovata, mostrerà uno spazio vuoto senza crashare
        ImageView imageView = new ImageView();
        try {
            Image img = new Image(imagePath);
            imageView.setImage(img);
        } catch (Exception e) {
            System.out.println("Immagine non trovata: " + imagePath);
        }
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        ProgressBar hpBar = new ProgressBar(healthProgress);
        hpBar.setPrefWidth(150);
        hpBar.setStyle("-fx-accent: green;"); // Colore della barra della vita

        Label hpLabel = new Label(hpText);

        VBox box = new VBox(5);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(nameLabel, imageView, hpBar, hpLabel);

        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
