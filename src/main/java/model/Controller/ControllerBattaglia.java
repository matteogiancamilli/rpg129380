package model.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Livello;
import model.CreatoreSalvataggi;

public class ControllerBattaglia {

    // ── Dati ──────────────────────────────────────────────
    private GestoreCombattimento gestore;

    // ── Riferimenti UI aggiornabili ───────────────────────
    private Label       battleLog;
    private ProgressBar hpBarGiocatore;
    private Label       hpLabelGiocatore;
    private ProgressBar hpBarMostro;
    private Label       hpLabelMostro;
    private VBox        abilitaBox;
    private Label       manaLabel;
    private Label       descrizioneAbilita;

    // ── Sezione oggetti ───────────────────────────────────
    private FlowPane    oggettiPane;   // griglia oggetti nell'inventario
    private Label       labelOggetto;  // tooltip testuale in basso

    // ── Configurazione ────────────────────────────────────
    public void setGestore(GestoreCombattimento gestore) {
        this.gestore = gestore;
    }

    // ── Costruzione scena ─────────────────────────────────
    public javafx.scene.Scene costruisciScena() {
        Personaggio p = gestore.getPersonaggio();
        Nemico      m = gestore.getMostro();

        // ── Arena ─────────────────────────────────────────
        hpBarGiocatore   = new ProgressBar(1.0);
        hpLabelGiocatore = new Label(testoHP(p.getVita(), p.getVitaMax()));
        VBox boxGiocatore = creaBoxPersonaggio(
                p.getNome(),
                "/images/images/classi/" + p.getClasse().getNome().toLowerCase() + ".png",
                hpBarGiocatore, hpLabelGiocatore
        );

        hpBarMostro   = new ProgressBar(1.0);
        hpLabelMostro = new Label(testoHP(m.getVitaCorrente(), m.getTipo().getVitaMassima()));
        VBox boxMostro = creaBoxPersonaggio(
                m.getTipo().getNome(),
                "/images/images/mostri/" + m.getTipo().name().toLowerCase() + ".png",
                hpBarMostro, hpLabelMostro
        );

        HBox arena = new HBox(120, boxGiocatore, boxMostro);
        arena.setAlignment(Pos.CENTER);
        arena.setPadding(new Insets(20));

        // ── Log ───────────────────────────────────────────
        battleLog = new Label(m.getTipo().getIntroduzione());
        battleLog.setWrapText(true);
        battleLog.getStyleClass().add("battle-log");
        battleLog.setPadding(new Insets(8));

        // ── Abilità ───────────────────────────────────────
        abilitaBox = new VBox(6);
        abilitaBox.setPadding(new Insets(8));
        for (Abilita a : p.getAbilitas()) {
            Button btn = new Button(testoBottone(a));
            btn.setPrefWidth(220);
            btn.setOnAction(e -> gestisciAzione(a));
            btn.setOnMouseEntered(e -> descrizioneAbilita.setText(a.getDescrizione()));
            btn.setOnMouseExited(e  -> descrizioneAbilita.setText(" "));
            abilitaBox.getChildren().add(btn);
        }

        manaLabel = new Label("Mana: " + p.getMana() + "/" + p.getManaMax());
        manaLabel.getStyleClass().add("mana-label");

        descrizioneAbilita = new Label(" ");
        descrizioneAbilita.setWrapText(true);
        descrizioneAbilita.getStyleClass().add("descrizione-abilita");

        // ── Tab Panel (Abilità | Oggetti) ─────────────────
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Tab Abilità
        VBox abilitaContent = new VBox(6, manaLabel, descrizioneAbilita, abilitaBox);
        abilitaContent.setPadding(new Insets(8));
        Tab tabAbilita = new Tab("Abilità", abilitaContent);

        // Tab Oggetti
        labelOggetto = new Label(" ");
        labelOggetto.setWrapText(true);
        labelOggetto.getStyleClass().add("label-oggetto");

        oggettiPane = new FlowPane(8, 8);
        oggettiPane.setPadding(new Insets(8));
        oggettiPane.setPrefWrapLength(380);
        aggiornaOggettiPane();

        VBox oggettiContent = new VBox(6, oggettiPane, labelOggetto);
        oggettiContent.setPadding(new Insets(4));
        Tab tabOggetti = new Tab("Oggetti", oggettiContent);

        tabPane.getTabs().addAll(tabAbilita, tabOggetti);

        // ── Sezione inferiore ─────────────────────────────
        VBox bottom = new VBox(6, battleLog, tabPane);
        bottom.setPadding(new Insets(10));

        // ── Layout radice ─────────────────────────────────
        BorderPane root = new BorderPane();
        root.setCenter(arena);
        root.setBottom(bottom);
        root.setMinSize(700, 680);   // garantisce che arena + barre HP siano sempre visibili

        javafx.scene.Scene scene = new javafx.scene.Scene(root, 700, 720);
        scene.getStylesheets().add(getClass().getResource("/javafx/battaglia.css").toExternalForm());
        return scene;
    }

    // ── Aggiorna la FlowPane degli oggetti ────────────────
    private void aggiornaOggettiPane() {
        oggettiPane.getChildren().clear();
        Personaggio p = gestore.getPersonaggio();

        java.util.List<Oggetto> lista = p.getInventario().getOggetti();

        if (lista.isEmpty()) {
            Label vuoto = new Label("Inventario vuoto");
            vuoto.getStyleClass().add("inventario-vuoto");
            oggettiPane.getChildren().add(vuoto);
            return;
        }

        for (Oggetto o : lista) {
            Button btn = new Button(o.getNomeVisuale());
            btn.setPrefWidth(170);
            btn.setWrapText(true);
            btn.getStyleClass().add(classeBotoneOggetto(o));

            // Tooltip con descrizione al passaggio del mouse
            btn.setOnMouseEntered(e -> labelOggetto.setText(o.getNomeVisuale() + ": " + o.getDescrizione()));
            btn.setOnMouseExited(e  -> labelOggetto.setText(" "));

            // Click → popup di conferma
            btn.setOnAction(e -> mostraPopupConferma(o));

            oggettiPane.getChildren().add(btn);
        }
    }

    // ── Classe CSS del bottone in base al tipo oggetto ────
    private String classeBotoneOggetto(Oggetto o) {
        return switch (o.getTipo()) {
            case 1 -> "btn-oggetto-cura";      // verde  – cura
            case 2 -> "btn-oggetto-danno";     // rosso  – danno
            case 3 -> "btn-oggetto-attacco";   // arancio – attacco
            case 4 -> "btn-oggetto-difesa";    // blu    – difesa
            case 5 -> "btn-oggetto-raro";      // viola  – raro
            default -> "button";
        };
    }

    // ── Popup di conferma uso oggetto ─────────────────────
    private void mostraPopupConferma(Oggetto oggetto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usa oggetto");
        alert.setHeaderText("Vuoi usare " + oggetto.getNomeVisuale() + "?");
        alert.setContentText("Effetto: " + oggetto.getDescrizione() +
                "\n\nL'oggetto verrà rimosso dall'inventario.\nIl turno rimarrà al tuo personaggio.");

        ButtonType btnUsa    = new ButtonType("Usa");
        ButtonType btnAnnulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnUsa, btnAnnulla);

        alert.showAndWait().ifPresent(risposta -> {
            if (risposta == btnUsa) {
                boolean usato = gestore.eseguiUsoOggetto(oggetto);
                if (usato) {
                    battleLog.setText("Hai usato " + oggetto.getNomeVisuale() + "! " + oggetto.getDescrizione());
                    aggiornaUI();
                    aggiornaOggettiPane();
                }
            }
        });
    }

    // ── Logica di un turno normale ────────────────────────
    private void gestisciAzione(Abilita abilita) {
        GestoreCombattimento.RisultatoTurno risultato = gestore.eseguiTurno(abilita);

        aggiornaUI();

        switch (risultato) {
            case OK ->
                    battleLog.setText("Hai usato " + abilita.getNome() + "! Il mostro ti attacca...");
            case MANA_INSUFFICIENTE ->
                    battleLog.setText("Mana insufficiente per " + abilita.getNome() + "!");
            case ABILITA_IN_COOLDOWN ->
                    battleLog.setText(abilita.getNome() + " è ancora in ricarica (" +
                            abilita.getCooldownCorrente() + " turni).");
            case MOSTRO_SCONFITTO -> {
                // ── Drop oggetto casuale ───────────────────
                Oggetto drop = Inventario.dropCasuale();
                gestore.getPersonaggio().getInventario().aggiungi(drop);
                battleLog.setText("Hai sconfitto " + gestore.getMostro().getTipo().getNome() +
                        "! Livello aumentato!\nHai ottenuto: " + drop.getNomeVisuale() + "!");
                disabilitaAzioni();

                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(2));
                pausa.setOnFinished(e -> navigaASchermataSuccessiva());
                pausa.play();
            }
            case PERSONAGGIO_SCONFITTO -> {
                battleLog.setText("Sei stato sconfitto da " + gestore.getMostro().getTipo().getNome() +
                        "... Ricarico il salvataggio!");
                disabilitaAzioni();

                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(5));
                pausa.setOnFinished(e -> caricaSalvataggioENaviga());
                pausa.play();
            }
            case MANA_ESAURITO -> {
                battleLog.setText("⚠ Non hai più mana né abilità disponibili... Sei spacciato!");
                disabilitaAzioni();

                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(2));
                pausa.setOnFinished(e -> caricaSalvataggioENaviga());
                pausa.play();
            }
        }
    }

    // ── Naviga alla schermata dialogo successiva ──────────
    private void navigaASchermataSuccessiva() {
        Personaggio p = gestore.getPersonaggio();
        int prossimoLivello = p.getLivello();

        if (prossimoLivello <= Livello.values().length) {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                        getClass().getResource("/javafx/dialogscreen.fxml"));
                javafx.scene.Parent root = loader.load();
                ControllerDialogScreen controller = loader.getController();
                controller.initData(p);

                Stage stage = (Stage) battleLog.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(root));
                stage.show();
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
            }
        } else {
            battleLog.setText("Hai completato il gioco! Sei il campione!");
        }
    }

    // ── Ricarica salvataggio e torna al dialogo ───────────
    private void caricaSalvataggioENaviga() {
        try {
            Personaggio pCaricato = new CreatoreSalvataggi().carica();
            if (pCaricato == null) {
                battleLog.setText("Nessun salvataggio trovato.");
                return;
            }
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/javafx/dialogscreen.fxml"));
            javafx.scene.Parent root = loader.load();
            ControllerDialogScreen controller = loader.getController();
            controller.initData(pCaricato);

            Stage stage = (Stage) battleLog.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            battleLog.setText("Errore nel caricare il salvataggio.");
        }
    }

    // ── Aggiorna barre HP, mana, bottoni abilità ──────────
    private void aggiornaUI() {
        Personaggio p = gestore.getPersonaggio();
        Nemico      m = gestore.getMostro();

        double percP = (double) p.getVita() / p.getVitaMax();
        hpBarGiocatore.setProgress(percP);
        aggiornaClasseHP(hpBarGiocatore, percP);
        hpLabelGiocatore.setText(testoHP(p.getVita(), p.getVitaMax()));

        double percM = (double) m.getVitaCorrente() / m.getTipo().getVitaMassima();
        hpBarMostro.setProgress(Math.max(0, percM));
        aggiornaClasseHP(hpBarMostro, percM);
        hpLabelMostro.setText(testoHP(Math.max(0, m.getVitaCorrente()), m.getTipo().getVitaMassima()));

        Abilita[] abilitas = p.getAbilitas();
        for (int i = 0; i < abilitaBox.getChildren().size(); i++) {
            if (abilitaBox.getChildren().get(i) instanceof Button btn) {
                btn.setText(testoBottone(abilitas[i]));
                btn.setDisable(!abilitas[i].abilitaPronta() ||
                        p.getMana() < abilitas[i].getCostoMana());
            }
        }
        manaLabel.setText("Mana: " + p.getMana() + "/" + p.getManaMax());
    }

    // ── Disabilita tutti i bottoni abilità ─────────────────
    private void disabilitaAzioni() {
        for (var node : abilitaBox.getChildren()) {
            if (node instanceof Button btn) btn.setDisable(true);
        }
    }

    // ── Helper: box combattente ───────────────────────────
    private VBox creaBoxPersonaggio(String nome, String pathImmagine,
                                    ProgressBar hpBar, Label hpLabel) {
        Label nomeLabel = new Label(nome);
        nomeLabel.getStyleClass().add("battle-name-label");

        ImageView imageView = new ImageView();
        try {
            var stream = getClass().getResourceAsStream(pathImmagine);
            if (stream != null) {
                imageView.setImage(new Image(stream));
            }
        } catch (Exception e) {
            System.out.println("Errore immagine: " + pathImmagine + " - " + e.getMessage());
        }
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        hpBar.setPrefWidth(150);
        hpBar.getStyleClass().add("hp-green");

        VBox box = new VBox(8, nomeLabel, imageView, hpBar, hpLabel);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    // ── Helper testi ──────────────────────────────────────
    private String testoHP(int attuale, int massimo) {
        return attuale + " / " + massimo + " HP";
    }

    private String testoBottone(Abilita a) {
        return a.getNome() + "  (M:" + a.getCostoMana() + ")" + a.stato();
    }

    // ── Aggiorna classe CSS della barra HP ────────────────
    private void aggiornaClasseHP(ProgressBar bar, double perc) {
        bar.getStyleClass().removeAll("hp-green", "hp-orange", "hp-red");
        if (perc > 0.5)       bar.getStyleClass().add("hp-green");
        else if (perc > 0.25) bar.getStyleClass().add("hp-orange");
        else                  bar.getStyleClass().add("hp-red");
    }
}
