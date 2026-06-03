package model.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Abilita;
import model.GestoreCombattimento;
import model.Mostro;
import model.Personaggio;

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

    // ── Configurazione (chiamata prima di costruireUI) ────
    public void setGestore(GestoreCombattimento gestore) {
        this.gestore = gestore;
    }

    // ── Punto di ingresso: restituisce la scena già montata ─
    public javafx.scene.Scene costruisciScena() {
        Personaggio p = gestore.getPersonaggio();
        Mostro      m = gestore.getMostro();

        // ── Arena (centro) ────────────────────────────────
        hpBarGiocatore   = new ProgressBar(1.0);
        hpLabelGiocatore = new Label(testoHP(p.getVita(), p.getVitaMax()));
        VBox boxGiocatore = creaBoxPersonaggio(p.getNome(), hpBarGiocatore, hpLabelGiocatore);

        hpBarMostro   = new ProgressBar(1.0);
        hpLabelMostro = new Label(testoHP(m.getVita(), m.getVitaMassima()));
        VBox boxMostro = creaBoxPersonaggio(m.getNome(), hpBarMostro, hpLabelMostro);

        HBox arena = new HBox(120, boxGiocatore, boxMostro);
        arena.setAlignment(Pos.CENTER);
        arena.setPadding(new Insets(20));

        // ── Log di battaglia ──────────────────────────────
        battleLog = new Label(m.getIntroduzione());
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
            abilitaBox.getChildren().add(btn);
        }

        // ── Sezione inferiore ─────────────────────────────
        Label manaLabel = new Label("Mana: " + p.getMana() + "/" + p.getManaMax());
        manaLabel.setStyle("-fx-font-size:12px;");

        VBox bottom = new VBox(6, battleLog, manaLabel, abilitaBox);
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
                battleLog.setText("Hai sconfitto " + gestore.getMostro().getNome() + "! Livello aumentato!");
                disabilitaAzioni();
            }
            case PERSONAGGIO_SCONFITTO -> {
                battleLog.setText("Sei stato sconfitto da " + gestore.getMostro().getNome() + "...");
                disabilitaAzioni();
            }
        }
    }

    // ── Aggiorna barre HP e testo bottoni ─────────────────
    private void aggiornaUI() {
        Personaggio p = gestore.getPersonaggio();
        Mostro      m = gestore.getMostro();

        // HP giocatore
        double percP = (double) p.getVita() / p.getVitaMax();
        hpBarGiocatore.setProgress(percP);
        hpBarGiocatore.setStyle(coloreHP(percP));
        hpLabelGiocatore.setText(testoHP(p.getVita(), p.getVitaMax()));

        // HP mostro
        double percM = (double) m.getVita() / m.getVitaMassima();
        hpBarMostro.setProgress(Math.max(0, percM));
        hpBarMostro.setStyle(coloreHP(percM));
        hpLabelMostro.setText(testoHP(Math.max(0, m.getVita()), m.getVitaMassima()));

        // Testo cooldown sui bottoni
        Abilita[] abilitas = p.getAbilitas();
        for (int i = 0; i < abilitaBox.getChildren().size(); i++) {
            if (abilitaBox.getChildren().get(i) instanceof Button btn) {
                btn.setText(testoBottone(abilitas[i]));
                btn.setDisable(!abilitas[i].abilitaPronta() ||
                        p.getMana() < abilitas[i].getCostoMana());
            }
        }
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
    private VBox creaBoxPersonaggio(String nome, ProgressBar hpBar, Label hpLabel) {
        Label nomeLabel = new Label(nome);
        nomeLabel.setStyle("-fx-font-weight:bold; -fx-font-size:15px;");

        hpBar.setPrefWidth(150);
        hpBar.setStyle("-fx-accent: green;");

        VBox box = new VBox(5, nomeLabel, hpBar, hpLabel);
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