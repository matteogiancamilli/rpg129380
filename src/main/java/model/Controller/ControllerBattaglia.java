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
    private VBox        abilitaBox;   // contenitore bottoni abilità
    private Label manaLabel;
    private Label descrizioneAbilita;

    // ── Configurazione (chiamata prima di costruireUI) ────
    public void setGestore(GestoreCombattimento gestore){
        this.gestore = gestore;
    }

    // ── Punto di ingresso: restituisce la scena già montata ─
    public javafx.scene.Scene costruisciScena() {
        Personaggio p = gestore.getPersonaggio();
        Nemico m = gestore.getMostro();

        // ── Arena (centro) ────────────────────────────────
        hpBarGiocatore   = new ProgressBar(1.0);
        hpLabelGiocatore = new Label(testoHP(p.getVita(), p.getVitaMax()));
        VBox boxGiocatore = creaBoxPersonaggio(
                p.getNome(),
                "/images.images/classi/" + p.getClasse().getNome().toLowerCase() + ".png",
                hpBarGiocatore,
                hpLabelGiocatore
        );

        hpBarMostro   = new ProgressBar(1.0);
        hpLabelMostro = new Label(testoHP(m.getVitaCorrente(), m.getTipo().getVitaMassima()));
        VBox boxMostro = creaBoxPersonaggio(
                m.getTipo().getNome(),
                "/images.images/mostri/" + m.getTipo().name().toLowerCase() + ".png",
                hpBarMostro,
                hpLabelMostro
        );

        HBox arena = new HBox(120, boxGiocatore, boxMostro);
        arena.setAlignment(Pos.CENTER);
        arena.setPadding(new Insets(20));

        // ── Log di battaglia ──────────────────────────────
        battleLog = new Label(m.getTipo().getIntroduzione());
        battleLog.setWrapText(true);
        battleLog.setStyle("-fx-font-size:13px; -fx-text-fill:#8b0000; -fx-font-weight:bold;");
        battleLog.setPadding(new Insets(8));

        // ── Bottoni abilità ───────────────────────────────
        abilitaBox = new VBox(6);
        abilitaBox.setPadding(new Insets(8));
        for (Abilita a : p.getAbilitas()) {
            Button btn = new Button(testoBottone(a));
            btn.setPrefWidth(220);
            btn.setOnAction(e -> gestisciAzione(a));

            // Mostra descrizione al passaggio del cursore
            btn.setOnMouseEntered(e -> descrizioneAbilita.setText(a.getDescrizione()));
            btn.setOnMouseExited(e -> descrizioneAbilita.setText(" "));

            abilitaBox.getChildren().add(btn);
        }

        // ── Sezione inferiore ─────────────────────────────
        manaLabel = new Label("Mana: " + p.getMana() + "/" + p.getManaMax());
        descrizioneAbilita = new Label(" ");
        descrizioneAbilita.setWrapText(true);
        descrizioneAbilita.setStyle("-fx-font-size:11px; -fx-text-fill:#444;");

        VBox bottom = new VBox(6, battleLog, manaLabel, descrizioneAbilita, abilitaBox);
        manaLabel.setStyle("-fx-font-size:12px;");
        bottom.setPadding(new Insets(10));

        // ── Layout radice ─────────────────────────────────
        BorderPane root = new BorderPane();
        root.setCenter(arena);
        root.setBottom(bottom);

        return new javafx.scene.Scene(root, 700, 520);
    }

    // ── Logica di un turno ────────────────────────────────
    private void gestisciAzione(Abilita abilita) {
        GestoreCombattimento.RisultatoTurno risultato = gestore.eseguiTurno(abilita);

        aggiornaUI();

        switch (risultato) {
            case OK ->
                    battleLog.setText("Hai usato " + abilita.getNome() +
                            "! Il mostro ti attacca...");
            case MANA_INSUFFICIENTE ->
                    battleLog.setText("Mana insufficiente per " + abilita.getNome() + "!");
            case ABILITA_IN_COOLDOWN ->
                    battleLog.setText("" + abilita.getNome() + " è ancora in ricarica " +
                            abilita.getCooldownCorrente() + " turni).");
            case MOSTRO_SCONFITTO -> {
                battleLog.setText("Hai sconfitto " + gestore.getMostro().getTipo().getNome() + "! Livello aumentato!");
                disabilitaAzioni();

                // Dopo 2 secondi apre la prossima schermata storia
                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(2)
                );
                pausa.setOnFinished(e -> {
                    Personaggio p = gestore.getPersonaggio();
                    int prossimoLivello = p.getLivello();

                    // Se ci sono ancora livelli, mostra la storia del prossimo mostro
                    if (prossimoLivello <= Livello.values().length) {
                        try {
                            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                                    getClass().getResource("/javafx/dialogscreen.fxml")
                            );
                            javafx.scene.Parent root = loader.load();
                            ControllerDialogScreen controller = loader.getController();
                            controller.initData(p);

                            Stage stageCorrente = (Stage) battleLog.getScene().getWindow();
                            stageCorrente.setScene(new javafx.scene.Scene(root));
                            stageCorrente.show();
                        } catch (java.io.IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        // Gioco completato
                        battleLog.setText("Hai completato il gioco! Sei il campione!");
                    }
                });
                pausa.play();
            }
            case PERSONAGGIO_SCONFITTO -> {
                battleLog.setText("Sei stato sconfitto da " + gestore.getMostro().getTipo().getNome() + "... Ricarico il salvataggio!");
                disabilitaAzioni();

                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(5)
                );
                pausa.setOnFinished(e -> {
                    try {
                        Personaggio pCaricato = new CreatoreSalvataggi().carica();
                        if (pCaricato == null) {
                            battleLog.setText("Nessun salvataggio trovato.");
                            return;
                        }

                        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                                getClass().getResource("/javafx/dialogscreen.fxml")
                        );
                        javafx.scene.Parent root = loader.load();
                        ControllerDialogScreen controller = loader.getController();
                        controller.initData(pCaricato);

                        Stage stageCorrente = (Stage) battleLog.getScene().getWindow();
                        stageCorrente.setScene(new javafx.scene.Scene(root));
                        stageCorrente.show();
                    } catch (java.io.IOException ex) {
                        ex.printStackTrace();
                        battleLog.setText("Errore nel caricare il salvataggio.");
                    }
                });
                pausa.play();
            }
            case MANA_ESAURITO -> {
                battleLog.setText("⚠Non hai più mana né abilità disponibili... Sei spacciato!");
                disabilitaAzioni();

                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                        javafx.util.Duration.seconds(2)
                );
                pausa.setOnFinished(e -> {
                    try {
                        Personaggio pCaricato = new CreatoreSalvataggi().carica();
                        if (pCaricato == null) {
                            battleLog.setText("Nessun salvataggio trovato.");
                            return;
                        }
                        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                                getClass().getResource("/dialogscreen.fxml")
                        );
                        javafx.scene.Parent root = loader.load();
                        ControllerDialogScreen controller = loader.getController();
                        controller.initData(pCaricato);

                        Stage stageCorrente = (Stage) battleLog.getScene().getWindow();
                        stageCorrente.setScene(new javafx.scene.Scene(root));
                        stageCorrente.show();
                    } catch (java.io.IOException ex) {
                        ex.printStackTrace();
                    }
                });
                pausa.play();
            }
        }
    }

    private void cambiScena() {
        disabilitaAzioni();

        javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(
                javafx.util.Duration.seconds(5)
        );
        pausa.setOnFinished(e -> {
            try {
                Personaggio pCaricato = new CreatoreSalvataggi().carica();
                if (pCaricato == null) {
                    battleLog.setText("Nessun salvataggio trovato.");
                    return;
                }

                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                        getClass().getResource("/javafx/dialogscreen.fxml")
                );
                javafx.scene.Parent root = loader.load();
                ControllerDialogScreen controller = loader.getController();
                controller.initData(pCaricato);

                Stage stageCorrente = (Stage) battleLog.getScene().getWindow();
                stageCorrente.setScene(new javafx.scene.Scene(root));
                stageCorrente.show();
            } catch (java.io.IOException ex) {
                ex.printStackTrace();
                battleLog.setText("Errore nel caricare il salvataggio.");
            }
        });
        pausa.play();

    }


    // ── Aggiorna barre HP e testo bottoni ─────────────────
    private void aggiornaUI() {
        Personaggio p = gestore.getPersonaggio();
        Nemico m = gestore.getMostro();

        // HP giocatore
        double percP = (double) p.getVita() / p.getVitaMax();
        hpBarGiocatore.setProgress(percP);
        hpBarGiocatore.setStyle(coloreHP(percP));
        hpLabelGiocatore.setText(testoHP(p.getVita(), p.getVitaMax()));

        // HP mostro
        double percM = (double) m.getVitaCorrente() / m.getTipo().getVitaMassima();
        hpBarMostro.setProgress(Math.max(0, percM));
        hpBarMostro.setStyle(coloreHP(percM));
        hpLabelMostro.setText(testoHP(Math.max(0, m.getVitaCorrente()), m.getTipo().getVitaMassima()));

        // Testo cooldown sui bottoni
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

    // ── Disabilita tutti i bottoni a fine battaglia ────────
    private void disabilitaAzioni() {
        for (var node : abilitaBox.getChildren()) {
            if (node instanceof Button btn) {
                btn.setDisable(true);
            }
        }
    }

    // ── Helper: crea il box di un combattente ─────────────
    private VBox creaBoxPersonaggio(String nome, String pathImmagine, ProgressBar hpBar, Label hpLabel) {
        Label nomeLabel = new Label(nome);
        nomeLabel.setStyle("-fx-font-weight:bold; -fx-font-size:15px;");

        ImageView imageView = new ImageView();
        try {
            var stream = getClass().getResourceAsStream(pathImmagine);
            if (stream != null) {
                imageView.setImage(new Image(stream));
            } else {
                System.out.println("Stream null per: " + pathImmagine);
            }
        } catch (Exception e) {
            System.out.println("Errore immagine: " + pathImmagine + " - " + e.getMessage());
        }
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        hpBar.setPrefWidth(150);
        hpBar.setStyle("-fx-accent: green;");

        VBox box = new VBox(8, nomeLabel, imageView, hpBar, hpLabel);
        box.setAlignment(Pos.CENTER);
        return box;
    }


    // ── Helper: testo "HP attuale / massimo" ──────────────
    private String testoHP(int attuale, int massimo) {
        return attuale + " / " + massimo + " HP";
    }

    // ── Helper: testo bottone con stato cooldown ──────────
    private String testoBottone(Abilita a) {
        return a.getNome() + "  (MP:" + a.getCostoMana() + ")" + a.stato();
    }

    // ── Helper: colore barra HP in base alla percentuale ──
    private String coloreHP(double perc) {
        if (perc > 0.5) return "-fx-accent: green;";
        if (perc > 0.25) return "-fx-accent: orange;";
        return "-fx-accent: red;";
    }
}