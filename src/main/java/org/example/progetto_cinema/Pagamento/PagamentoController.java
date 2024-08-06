package org.example.progetto_cinema.Pagamento;

import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.Payment_Strategy.IMetodoPagamentoStrategy;
import backend.Payment_Strategy.PagamentoBancomatStrategy;
import backend.Payment_Strategy.PagamentoCartaDiCreditoStrategy;
import backend.Payment_Strategy.PagamentoContantiStrategy;
import backend.Revenues_Observer.Concrete_Observable.RegistroBiglietti;
import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.Serializzazione.Adaptee.SalaSerializer;
import backend.Serializzazione.Adaptee.SpettacoloSerializer;
import backend.Serializzazione.Adapter.SalaSerializerAdapter;
import backend.Serializzazione.Adapter.SpettacoloSerializerAdapter;
import backend.Ticket_Factory.IBiglietto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.progetto_cinema.General_Utility_Classes.AlertUtil;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISalaDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISpettacoloDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.SalaDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.SpettacoloDataSerializer;
import org.example.progetto_cinema.Pagamento.Service.IPagamentoService;
import org.example.progetto_cinema.Pagamento.Service.PagamentoService;
import org.example.progetto_cinema.UserDashboardController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PagamentoController {

    @FXML
    private Button back_btn;

    @FXML
    private Button annulla_button;

    @FXML
    private Button pagaConBancomat_button;

    @FXML
    private Button pagaConCarta_button;

    @FXML
    private Button pagaInContanti_button;
    @FXML
    private ListView<IBiglietto> listaBiglietti;

    @FXML
    private TilePane tilePaneBiglietti;

    @FXML
    private List<ISpettacolo> spettacoli;

    @FXML
    private IPagamentoService pagamentoService;

    @FXML
    private Utente utente;

    @FXML
    private List<IBiglietto> bigliettiDaAcquistare;

    @FXML
    private List<ISala> sale;

    @FXML
    private AbstractRegistroBiglietti registroBiglietti;

    @FXML
    private IMetodoPagamentoStrategy metodoPagamentoContanti;

    @FXML
    private IMetodoPagamentoStrategy metodoPagamentoCartaDiCredito;

    @FXML
    private IMetodoPagamentoStrategy metodoPagamentoBancomat;

    private ISpettacoloDataSerializer spettacoloDataSerializer;
    private ISalaDataSerializer salaDataSerializer;


    @FXML
    public void initialize() {
        spettacoloDataSerializer = new SpettacoloDataSerializer(new SpettacoloSerializerAdapter(new SpettacoloSerializer()));
        salaDataSerializer = new SalaDataSerializer(new SalaSerializerAdapter(new SalaSerializer()));

        bigliettiDaAcquistare = new ArrayList<>();
        registroBiglietti = new RegistroBiglietti();

        metodoPagamentoContanti = new PagamentoContantiStrategy();
        metodoPagamentoCartaDiCredito = new PagamentoCartaDiCreditoStrategy();
        metodoPagamentoBancomat = new PagamentoBancomatStrategy();

        try {
            sale = salaDataSerializer.caricaSala();
        } catch (Exception e) {
            System.out.println("Impossibile caricare le sale esistenti. " + e.getMessage());
            sale = new ArrayList<>();
        }

        try {
            spettacoli = spettacoloDataSerializer.caricaSpettacoli();
        } catch (Exception e) {
            System.out.println("Impossibile caricare gli spettacoli esistenti. " + e.getMessage());
            spettacoli = new ArrayList<>();
        }
        pagamentoService = new PagamentoService(registroBiglietti, spettacoli, sale);
    }

    public void aggiungiBigliettiAlTilePane(List<IBiglietto> biglietti) {
        for (IBiglietto biglietto : biglietti) {
            VBox pannelloBiglietto = new VBox(10);
            pannelloBiglietto.setPadding(new Insets(10));
            pannelloBiglietto.setBackground(new Background(new BackgroundFill(Color.web(biglietto.getColore()), CornerRadii.EMPTY, Insets.EMPTY)));

            // Crea Label con testo bianco
            Label nomeAcquirente = new Label(biglietto.getAcquirente().getNome() + " " + biglietto.getAcquirente().getCognome());
            Label costo = new Label(String.format("Costo: %.2f €", biglietto.getCosto()));
            Label film = new Label("Film: " + biglietto.getSpettacolo().getFilm().getTitolo());
            Label sala = new Label("Sala: " + biglietto.getSpettacolo().getSala().getNumeroSala());
            //Label orario = new Label("Ora: " + biglietto.getSpettacolo().getOrarioProiezione().toString());
            // Usa DateTimeFormatter per formattare la data e l'ora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss");
            String orarioFormattato = biglietto.getSpettacolo().getOrarioProiezione().format(formatter);
            Label orario = new Label("Ora: " + orarioFormattato);

            // Imposta lo stile dei Label per avere il testo bianco
            String labelStyle = "-fx-text-fill: white;";
            nomeAcquirente.setStyle(labelStyle);
            costo.setStyle(labelStyle);
            film.setStyle(labelStyle);
            sala.setStyle(labelStyle);
            orario.setStyle(labelStyle);

            // Aggiunge tutti i Label al VBox
            pannelloBiglietto.getChildren().addAll(nomeAcquirente, costo, film, sala, orario);

            // Aggiunge il VBox al TilePane
            tilePaneBiglietti.getChildren().add(pannelloBiglietto);
        }
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
        // imposta l'utente anche nel PagamentoService
        this.pagamentoService.setUtente(utente);
    }

    public void setBigliettiDaAcquistare(List<IBiglietto> biglietti) {
        this.bigliettiDaAcquistare = biglietti;
    }


    @FXML
    private void pagaInContanti() throws IOException, ClassNotFoundException {

        if (pagamentoService.eseguiPagamento(bigliettiDaAcquistare, metodoPagamentoContanti)) {
            AlertUtil.showAlert("Operazione Completata","Acquisto in contanti completato con successo!");
            aggiungiBigliettiAlTilePane(bigliettiDaAcquistare);
        }
    }

    @FXML
    private void pagaConCarta() throws IOException, ClassNotFoundException {

        if (pagamentoService.eseguiPagamento(bigliettiDaAcquistare, metodoPagamentoCartaDiCredito)) {
            AlertUtil.showAlert("Operazione Completata","Acquisto con carta completato con successo!");
            aggiungiBigliettiAlTilePane(bigliettiDaAcquistare);
        }
    }

    @FXML
    private void pagaConBancomat() throws IOException, ClassNotFoundException {

        if (pagamentoService.eseguiPagamento(bigliettiDaAcquistare, metodoPagamentoBancomat)) {
            AlertUtil.showAlert("Operazione Completata","Acquisto con Bancomat completato con successo!");
            aggiungiBigliettiAlTilePane(bigliettiDaAcquistare);
        }
    }


    public void annullaUltimoAcquisto() throws IOException, ClassNotFoundException {
        pagamentoService.annullaUltimoAcquisto(bigliettiDaAcquistare, this::rimuoviUltimoBigliettoDalTilePane);
    }

    private void rimuoviUltimoBigliettoDalTilePane() {
        Platform.runLater(() -> {
            if (!tilePaneBiglietti.getChildren().isEmpty()) {
                tilePaneBiglietti.getChildren().remove(tilePaneBiglietti.getChildren().size() - 1);
            }
        });
    }

    @FXML
    private void handleBackButton() {
        try {
            // Carica la nuova scena
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/interfaccia_grafica/user_dashboard.fxml"));
            Parent root = loader.load();

            // Ottieni il controller della nuova scena
            UserDashboardController controller = loader.getController();
            controller.setUtente(utente); // Passa i dati dell'utente al controller


            // Ottieni la scena attuale e il palco
            Stage stage = (Stage) back_btn.getScene().getWindow(); // Assumi che comeBackButton sia definito come @FXML Button

            // Imposta la nuova scena sul palco
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
