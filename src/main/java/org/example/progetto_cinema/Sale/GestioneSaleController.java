package org.example.progetto_cinema.Sale;

import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Sala.Sala;
import backend.Domain.Amministratore;
import backend.Domain.Ruolo;
import backend.Exception.Sala.NumeroPostiNegativoException;
import backend.Exception.Sala.NumeroSalaNegativoException;
import backend.Exception.Sala.SalaGiaEsistenteException;
import backend.Exception.Sala.SalaNonTrovataException;
import backend.ID_Persistente.GeneratoreIDPersistenteSala;
import backend.ID_Persistente.IGeneratoreIDPersistente;
import backend.Serializzazione.Adaptee.SalaSerializer;
import backend.Serializzazione.Adapter.SalaSerializerAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.progetto_cinema.General_Utility_Classes.AlertUtil;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISalaDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.SalaDataSerializer;
import org.example.progetto_cinema.Sale.Service.GestioneSaleService;
import org.example.progetto_cinema.Sale.Service.IGestioneSaleService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class GestioneSaleController implements Initializable {

    @FXML
    private TableView<ISala> sala_tableview;

    @FXML
    private TableColumn<Sala, Integer> numeroSalaCol_tableview;

    @FXML
    private TableColumn<Sala, Integer> capacitaCol_tableview;

    @FXML
    private TableColumn<Sala, Long> IDCol_tableview;

    @FXML
    private TextField IDRimuoviSala_textfiel;

    @FXML
    private Button aggiungiSala_btn;

    @FXML
    private TextField capacitaSala_textflied;

    @FXML
    private Button eliminaSala_btn;

    @FXML
    private TextField numeroSala_textfiel;

    private ObservableList<ISala> saleObservableList = FXCollections.observableArrayList();

    private List<ISala> sale;

    private IGestioneSaleService gestioneSaleService;

    private ISalaDataSerializer salaDataSerializer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salaDataSerializer = new SalaDataSerializer(new SalaSerializerAdapter(new SalaSerializer()));

        try {
            sale =  salaDataSerializer.caricaSala();
            saleObservableList.addAll(sale);
        } catch (Exception e) {
            System.out.println("Impossibile caricare le sale esistenti. " + e.getMessage());
            sale = new ArrayList<>();
        }

        IGeneratoreIDPersistente generatoreIDSala = new GeneratoreIDPersistenteSala();
        Amministratore amministratore = new Amministratore("Mario","Rossi", Ruolo.AMMINISTRATORE);
        this.gestioneSaleService = new GestioneSaleService(sale, generatoreIDSala, salaDataSerializer, amministratore);

        numeroSalaCol_tableview.setCellValueFactory(new PropertyValueFactory<>("numeroSala"));
        capacitaCol_tableview.setCellValueFactory(new PropertyValueFactory<>("capacita"));
        IDCol_tableview.setCellValueFactory(new PropertyValueFactory<>("id"));
        sala_tableview.setItems(saleObservableList);
    }

    @FXML
    public void aggiungiSala() {
        try {
            int numeroSala = Integer.parseInt(numeroSala_textfiel.getText());
            int capacita = Integer.parseInt(capacitaSala_textflied.getText());

            gestioneSaleService.aggiungiSala(numeroSala, capacita, nuovaSala -> {
                //AlertUtil.showInformationAlert("Sala aggiunta con successo!");
                AlertUtil.showAlert("Operazione Completata","Sala aggiunta con successo!");
                aggiungiSalaTable(nuovaSala);
            });
        } catch (NumeroSalaNegativoException | NumeroPostiNegativoException e) {
            AlertUtil.showErrorAlert("Il numero della sala e la capacità devono essere positivi.");
        } catch (SalaGiaEsistenteException e) {
            AlertUtil.showErrorAlert("La sala esiste già.");
        } catch (NumberFormatException e) {
            AlertUtil.showErrorAlert("Numero della sala o capacità non validi.");
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Errore imprevisto durante l'aggiunta della sala: " + e.getMessage());
        }
    }


    @FXML
    public void rimuoviSala() {
        try {
            long idSala = Long.parseLong(IDRimuoviSala_textfiel.getText());

            Consumer<Long> onSuccess = idSalaRimosso -> {
                AlertUtil.showAlert("Operazione Completata","Sala rimossa con successo!");
                rimuoviSalaTable(idSalaRimosso);
            };
            gestioneSaleService.rimuoviSala(idSala, onSuccess);

        } catch (NumberFormatException e) {
            AlertUtil.showErrorAlert("ID della sala non valido.");
        } catch (SalaNonTrovataException e) {
            AlertUtil.showErrorAlert("Sala non trovata.");
        } catch (Exception e) {
            AlertUtil.showErrorAlert(e.getMessage());
        }
    }

    public void aggiungiSalaTable(ISala nuovaSala) {
        saleObservableList.add(nuovaSala);
    }

    public void rimuoviSalaTable(long idSalaDaRimuovere) {
        // Trova la sala con l'ID specificato
        ISala salaTrovata = null;
        for (ISala sala : saleObservableList) {
            if (sala.getId() == idSalaDaRimuovere) {
                salaTrovata = sala;
                break;
            }
        }

        // Rimuovi la sala trovata dalla lista osservabile, se presente
        if (salaTrovata != null) {
            saleObservableList.remove(salaTrovata);
        }
    }
}
