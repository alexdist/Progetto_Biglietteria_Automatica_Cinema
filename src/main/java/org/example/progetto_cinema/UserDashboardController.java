package org.example.progetto_cinema;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.ID_Persistente.GeneratoreIDPersistenteBiglietti;
import backend.ID_Persistente.IGeneratoreIDPersistente;
import backend.Serializzazione.Adaptee.SpettacoloSerializer;
import backend.Serializzazione.Adapter.SpettacoloSerializerAdapter;
import backend.Ticket_Factory.IBiglietto;
import backend.Ticket_Pricing.IPrezziBiglietto;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.progetto_cinema.General_Utility_Classes.AlertUtil;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISpettacoloDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.SpettacoloDataSerializer;
import org.example.progetto_cinema.Pagamento.PagamentoController;
import org.example.progetto_cinema.Service_Userdashcontroller.GestoreAcquisti;
import org.example.progetto_cinema.Service_Userdashcontroller.IGestoreAcquisti;
import org.example.progetto_cinema.Service_Userdashcontroller.IServizioPrezziBiglietto;
import org.example.progetto_cinema.Service_Userdashcontroller.ServizioPrezziBiglietto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDashboardController {

    @FXML
    private TableColumn<ISpettacolo, String> posti_disponibili_tableview1;

    @FXML
    private Button close;

    @FXML
    private Button esci_btn;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane topForm_anchorpane;

    @FXML
    private Label label_utente;

    @FXML
    private TableView<ISpettacolo> spettacoli_user_tableview;

    @FXML
    private TableColumn<ISpettacolo, LocalDateTime> data_user_tableview;

    @FXML
    private TableColumn<ISpettacolo, String> genereFilm_user_tableview;

    @FXML
    private TableColumn<ISpettacolo, String> sala_user_tableview;

    @FXML
    private TableColumn<ISpettacolo, String> titoloFilm_user_tableview;

    @FXML
    private Label selectDataFilm_label;

    @FXML
    private Label selectGenereFilm_label;

    @FXML
    private Label selectTitoloFilm_label;

    @FXML
    private Button select_button;

    @FXML
    private Spinner<Integer> numeroBiglietti_spinner;

    @FXML
    private Button procediAcquistoBigliettoButton;

    @FXML
    private Label prezzoParziale_label;

    @FXML
    private Label prezzoTotale_label;

    @FXML
    private ISpettacolo spettacoloSelezionato;

    @FXML
    private List<IBiglietto> bigliettiCreati = new ArrayList<>();

    @FXML
    private double x = 0;
    @FXML
    private double y = 0;

    @FXML
    private Utente utente; // passato dalla classe UserDashboardController

    @FXML
    IGeneratoreIDPersistente generatoreID;

    @FXML
    private IGestoreAcquisti gestoreAcquisti;

    @FXML
    private IServizioPrezziBiglietto prezziBigliettoDaFile;

    @FXML
    private ISpettacoloDataSerializer spettacoloDataSerializer;


    @FXML

    public void initialize() {
        prezziBigliettoDaFile = new ServizioPrezziBiglietto();

        spettacoloDataSerializer = new SpettacoloDataSerializer(new SpettacoloSerializerAdapter(new SpettacoloSerializer()));

        generatoreID = new GeneratoreIDPersistenteBiglietti();

        this.gestoreAcquisti = new GestoreAcquisti(generatoreID, prezziBigliettoDaFile.getPrezziBiglietto());

        spettacoli_user_tableview.refresh();

        // Configurazione dello Spinner per il numero di biglietti
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0);
        numeroBiglietti_spinner.setValueFactory(valueFactory);

        // Aggiunge un ChangeListener allo spinner dei numeri di biglietti
        numeroBiglietti_spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                aggiornaPrezzoParziale();
            }
        });

        // Configura i cellValueFactory per ogni colonna utilizzando i nomi specificati
        titoloFilm_user_tableview.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFilm().getTitolo()));

        sala_user_tableview.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getSala().getNumeroSala())));

        data_user_tableview.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrarioProiezione()));

        // Imposta il Custom CellFactory per formattare l'orario di proiezione
        data_user_tableview.setCellFactory(column -> {
            return new TableCell<ISpettacolo, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        // Formattazione della data e dell'orario
                        setText(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                    }
                }
            };
        });

        posti_disponibili_tableview1.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSala().getPostiDisponibili() + " disponibili")
        );
        posti_disponibili_tableview1.setCellFactory(column -> new TableCell<ISpettacolo, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // Pulisce lo stile precedente
                setText(null);
                setStyle("");

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    // Estrae il numero di posti disponibili dal testo
                    String[] parts = item.split(" ");
                    int postiDisponibili = Integer.parseInt(parts[0]);

                    // Imposta il testo della cella
                    setText(item);

                    // Se i posti disponibili sono meno di 5, colora il testo di rosso
                    if (postiDisponibili < 5) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("-fx-text-fill: black;"); // usa il nero per valori superiori a 5
                    }
                }
            }
        });

        genereFilm_user_tableview.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFilm().getGenere()));


        List<ISpettacolo> spettacoli = spettacoloDataSerializer.caricaSpettacoli();
        spettacoli_user_tableview.setItems(FXCollections.observableList(spettacoli));

        // Aggiunge un listener alla selezione degli elementi nella TableView
        spettacoli_user_tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ISpettacolo spettacoloSelezionato = spettacoli_user_tableview.getSelectionModel().getSelectedItem();

                // Aggiorna le Label con i dati dello spettacolo selezionato
                aggiornaLabelConSpettacoloSelezionato(spettacoloSelezionato);
            }
        });
    }

    private void aggiornaPrezzoParziale() {

        if (numeroBiglietti_spinner.getValue() != null) {

            int numeroBiglietti = numeroBiglietti_spinner.getValue();
            // Ottiene i prezzi aggiornati dal servizio
            IPrezziBiglietto prezziAttuali = prezziBigliettoDaFile.getPrezziBiglietto();

            double prezzoApplicato = utente.getEta() < 14 ? prezziAttuali.getPrezzoRidotto() : prezziAttuali.getPrezzoIntero();
            double prezzoParziale = numeroBiglietti * prezzoApplicato;
            prezzoParziale_label.setText(String.format("%.2f€", prezzoParziale));
            prezzoTotale_label.setText(String.format("%.2f€", prezzoParziale));

        }
    }


    public void selezionaSpettacolo() {
        ISpettacolo spettacoloSelezionatoTemp = spettacoli_user_tableview.getSelectionModel().getSelectedItem();

        if (spettacoloSelezionatoTemp != null) {
            // Imposta lo spettacolo selezionato
            spettacoloSelezionato = spettacoloSelezionatoTemp;

            LocalDateTime dataOrarioProiezione = spettacoloSelezionato.getOrarioProiezione();

            // Crea un oggetto DateTimeFormatter e specifica il formato desiderato
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Formatta la data
            String dataFormattata = dataOrarioProiezione.format(formatoData);

            // Imposta il testo formattato nel label
            selectDataFilm_label.setText(dataFormattata);

            // Aggiorna le label con i dettagli dello spettacolo
            selectTitoloFilm_label.setText(spettacoloSelezionato.getFilm().getTitolo());
            selectGenereFilm_label.setText(spettacoloSelezionato.getFilm().getGenere());


            // Chiama il metodo per applicare le strategie di prezzo al prezzo del biglietto
            prezziBigliettoDaFile.applicaStrategieDiPrezzo(spettacoloSelezionato);
            aggiornaPrezzoParziale();

            // Preparazione per il passo successivo (numero biglietti già impostato tramite Spinner)
        }
    }

    @FXML
    public void procediAcquisto() {
        List<IBiglietto> bigliettiCreati = gestoreAcquisti.procediAcquisto(spettacoloSelezionato, utente, numeroBiglietti_spinner.getValue());

        if (bigliettiCreati.isEmpty()) {
            AlertUtil.showErrorAlert("Impossibile procedere all'acquisto. Posti a sedere esauriti o numero biglietti selezionati maggiore del numero di posti a sedere disponibili.");
            return;
        }

        visualizzaInformazioniBiglietti();
        caricaSchermataPagamento(bigliettiCreati);
    }

    private void visualizzaInformazioniBiglietti() {
        for (IBiglietto biglietto : bigliettiCreati) {
            Utente acquirente = biglietto.getAcquirente();
            System.out.println("Biglietto ID: " + biglietto.getId());
            System.out.println("Nome: " + acquirente.getNome());
            System.out.println("Cognome: " + acquirente.getCognome());
            System.out.println("Prezzo: " + biglietto.getCosto() + "€");

            System.out.println("-------------------------------------");
        }
    }

    public void caricaSchermataPagamento(List<IBiglietto> bigliettiCreati) {
        try {
            // Carica il nuovo contenuto FXML per il centro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pagamento.fxml"));
            AnchorPane pagamentoPane = loader.load();

            // Ottiene l'accesso al controller associato alla vista caricata
            PagamentoController pagamentoController = loader.getController();

            // Passa l'oggetto Utente e la lista dei biglietti al controller della schermata di pagamento
            pagamentoController.setUtente(this.utente);
            pagamentoController.setBigliettiDaAcquistare(bigliettiCreati); // Usa la lista dei biglietti passata come parametro

            // Sostituisce il contenuto del centro nel BorderPane
            mainBorderPane.setCenter(pagamentoPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void aggiornaLabelConSpettacoloSelezionato(ISpettacolo spettacolo) {
        selectTitoloFilm_label.setText(spettacolo.getFilm().getTitolo());
        selectGenereFilm_label.setText(spettacolo.getFilm().getGenere());
        selectDataFilm_label.setText(spettacolo.getOrarioProiezione().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
        updateLabelUtente();
        // Aggiorna la vista della dashboard
    }

    public void logout() throws IOException {
        esci_btn.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {

                x = event.getSceneX();
                y = event.getSceneY();

            });

            root.setOnMouseDragged((MouseEvent event) -> {

                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);

            });

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo per aggiornare la label con il nome e cognome dell'utente
    private void updateLabelUtente() {

        if (utente != null && label_utente != null) {
            String nome = utente.getNome(); // la classe Utente ha il metodo getNome()
            String cognome = utente.getCognome(); // la classe Utente ha il metodo getCognome()
            label_utente.setText(nome + " " + cognome);
        }
    }

    public void closeDashboard() {

        System.exit(0);
    }

    public void minimizeDashboard() {

        Stage stage = (Stage) topForm_anchorpane.getScene().getWindow();
        stage.setIconified(true);
    }
}
