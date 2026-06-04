package it.unicam.cs.mpgc.rpg129380.controller;

import it.unicam.cs.mpgc.rpg129380.model.gioco.GestoreCombattimento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import it.unicam.cs.mpgc.rpg129380.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import it.unicam.cs.mpgc.rpg129380.model.nemici.Nemico;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Abilita;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Drop;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Oggetto;
import it.unicam.cs.mpgc.rpg129380.model.personaggio.Personaggio;

public class ControllerBattaglia {

    private GestoreCombattimento gestore;
    private NavigatoreSchermate navigatoreSchermate;
    private static final Duration PAUSA_VITTORIA  = Duration.seconds(2);
    private static final Duration PAUSA_SCONFITTA = Duration.seconds(5);

    @FXML private Label battleLog;
    @FXML private ProgressBar hpBarGiocatore;
    @FXML private Label hpLabelGiocatore;
    @FXML private ProgressBar hpBarMostro;
    @FXML private Label hpLabelMostro;
    @FXML private VBox abilitaBox;
    @FXML private Label manaLabel;
    @FXML private Label descrizioneAbilita;
    @FXML private Label nomeGiocatore;
    @FXML private Label nomeMostro;
    @FXML private ImageView immagineGiocatore;
    @FXML private ImageView immagineMostro;
    @FXML private FlowPane oggettiPane;
    @FXML private Label labelOggetto;

    public ControllerBattaglia(){}

    public void setNavigatore(NavigatoreSchermate navigatore) {
        this.navigatoreSchermate = navigatore;
    }

    public void initData(GestoreCombattimento gestore) {
        this.gestore = gestore;
        Personaggio p = gestore.getPersonaggio();
        Nemico m = gestore.getMostro();

        // Setup Nomi
        nomeGiocatore.setText(p.getNome());
        nomeMostro.setText(m.getTipo().getNome());

        // Setup Immagini
        impostaImmagine(immagineGiocatore, "/images/images/classi/" + p.getClasse().getNome().toLowerCase() + ".png");
        impostaImmagine(immagineMostro, "/images/images/mostri/" + m.getTipo().name().toLowerCase() + ".png");

        // Setup Log iniziale
        battleLog.setText(m.getTipo().getIntroduzione());

        // Creazione bottoni Abilità
        for (Abilita a : p.getAbilitas()) {
            Button btn = new Button(testoBottone(a));
            btn.setPrefWidth(220);
            btn.setOnAction(e -> gestisciAzione(a));
            btn.setOnMouseEntered(e -> descrizioneAbilita.setText(a.getDescrizione()));
            btn.setOnMouseExited(e -> descrizioneAbilita.setText(" "));
            abilitaBox.getChildren().add(btn);
        }

        aggiornaOggettiPane();
        aggiornaUI();
    }

    private void impostaImmagine(ImageView view, String path) {
        try {
            var stream = getClass().getResourceAsStream(path);
            if (stream != null) {
                view.setImage(new Image(stream));
            }
        } catch (Exception e) {
            System.out.println("Immagine non trovata: " + path);
        }
    }

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
            btn.getStyleClass().add(o.getTipoOggetto().getClasseCSS());
            btn.setOnMouseEntered(e -> labelOggetto.setText(o.getNomeVisuale() + ": " + o.getDescrizione()));
            btn.setOnMouseExited(e  -> labelOggetto.setText(" "));
            btn.setOnAction(e -> mostraPopupConferma(o));
            oggettiPane.getChildren().add(btn);
        }
    }

    private void mostraPopupConferma(Oggetto oggetto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usa oggetto");
        alert.setHeaderText("Vuoi usare " + oggetto.getNomeVisuale() + "?");
        alert.setContentText("Effetto: " + oggetto.getDescrizione() +
                "\n\nL'oggetto verrà rimosso dall'inventario.\nIl turno rimarrà al tuo personaggio.");

        ButtonType btnUsa = new ButtonType("Usa");
        ButtonType btnAnnulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnUsa, btnAnnulla);

        alert.showAndWait().ifPresent(risposta -> {
            if (risposta == btnUsa) {
                if (gestore.eseguiUsoOggetto(oggetto)) {
                    battleLog.setText("Hai usato " + oggetto.getNomeVisuale() + "! " + oggetto.getDescrizione());
                    aggiornaUI();
                    aggiornaOggettiPane();
                }
            }
        });
    }

    private void gestisciAzione(Abilita abilita) {
        GestoreCombattimento.RisultatoTurno risultato = gestore.eseguiTurno(abilita);
        aggiornaUI();

        switch (risultato) {
            case OK -> battleLog.setText("Hai usato " + abilita.getNome() + "! Il mostro ti attacca...");
            case MANA_INSUFFICIENTE -> battleLog.setText("Mana insufficiente per " + abilita.getNome() + "!");
            case ABILITA_IN_COOLDOWN -> battleLog.setText(abilita.getNome() + " è ancora in ricarica (" + abilita.getCooldownCorrente() + " turni).");
            case MOSTRO_SCONFITTO -> {
                Oggetto drop = Drop.dropCasuale();
                gestore.getPersonaggio().getInventario().aggiungi(drop);
                battleLog.setText("Hai sconfitto " + gestore.getMostro().getTipo().getNome() +
                        "!\nHai ottenuto: " + drop.getNomeVisuale() + "!");
                disabilitaAzioni();
                javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(PAUSA_VITTORIA);
                pausa.setOnFinished(e -> {
                    Stage stageAttuale = (Stage) battleLog.getScene().getWindow();
                    navigatoreSchermate.navigaASchermataSuccessiva(stageAttuale, gestore.getPersonaggio());
                });
                pausa.play();
            }
            case PERSONAGGIO_SCONFITTO -> {
                battleLog.setText("Sei stato sconfitto... Ricarico il salvataggio!");
                transizioneSconfitta();
            }
            case MANA_ESAURITO -> {
                battleLog.setText("⚠ Non hai più mana né abilità disponibili... Sei spacciato!");
                transizioneSconfitta();
            }
        }
    }

    private void transizioneSconfitta(){
        disabilitaAzioni();
        javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(PAUSA_SCONFITTA);
        pausa.setOnFinished(e -> {
            Stage stageAttuale = (Stage) battleLog.getScene().getWindow();
            navigatoreSchermate.caricaSalvataggioENaviga(stageAttuale);
        });
        pausa.play();
    }

    private void aggiornaUI() {
        Personaggio p = gestore.getPersonaggio();
        Nemico m = gestore.getMostro();

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
                btn.setDisable(!abilitas[i].abilitaPronta() || p.getMana() < abilitas[i].getCostoMana());
            }
        }
        manaLabel.setText("Mana: " + p.getMana() + "/" + p.getManaMax());
    }

    private void disabilitaAzioni() {
        for (var node : abilitaBox.getChildren()) {
            if (node instanceof Button btn) btn.setDisable(true);
        }
    }

    private String testoHP(int attuale, int massimo) {
        return attuale + " / " + massimo + " HP";
    }

    private String testoBottone(Abilita a) {
        return a.getNome() + "  (M:" + a.getCostoMana() + ")" + a.stato();
    }

    private void aggiornaClasseHP(ProgressBar bar, double perc) {
        bar.getStyleClass().removeAll("hp-green", "hp-orange", "hp-red");
        if (perc > 0.5)       bar.getStyleClass().add("hp-green");
        else if (perc > 0.25) bar.getStyleClass().add("hp-orange");
        else                  bar.getStyleClass().add("hp-red");
    }
}