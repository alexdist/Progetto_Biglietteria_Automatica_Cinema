package org.example.progetto_cinema;

import backend.Admin_Commands.Revenues.GeneraReportRicaviCommand;
import backend.Admin_Interfaces.ICommand;
import backend.Domain.Amministratore;
import backend.Domain.Ruolo;
import backend.Exception.Film.*;
import backend.Exception.Sala.SalaGiaEsistenteException;
import backend.Exception.Sala.SalaNonTrovataException;
import backend.Exception.Sala.SalaNonValidaException;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;
import backend.Revenues_Observer.Concrete_Observable.RegistroBiglietti;
import backend.Revenues_Observer.Concrete_ObservableA.AffluenzaPerSalaReport;
import backend.Revenues_Observer.Concrete_ObservableB.RicaviPerSalaReport;
import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.progetto_cinema.Ricavi.DatiSala;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestioneRicaviController {

    @FXML
    private TableColumn<DatiSala, Number> affluenzaSalaCol_tableview;
    @FXML
    private Button generaRepRicavi_btn;
    @FXML
    private TableColumn<DatiSala, Number> ricaviRicavoCol_tableview;
    @FXML
    private TableColumn<DatiSala, String> ricaviSalaCol_tableview;
    @FXML
    private TableView<DatiSala> ricavi_tableview;

    private Amministratore amministratore;
    private AbstractRegistroBiglietti registroBiglietti;
    private RicaviPerSalaReport ricaviReport;
    private AffluenzaPerSalaReport affluenzaReport;

    public GestioneRicaviController() {
        amministratore = new Amministratore("Luca", "Rossi", Ruolo.AMMINISTRATORE);
    }

    @FXML
    private void initialize() {
        try {
            caricaRegistroBiglietti();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Errore nel caricamento del registro dei biglietti.");
            registroBiglietti = new RegistroBiglietti(); // Fallback se il file non esiste o c'è un errore
        }

        ricaviReport = new RicaviPerSalaReport(registroBiglietti);
        affluenzaReport = new AffluenzaPerSalaReport(registroBiglietti);

        ricaviSalaCol_tableview.setCellValueFactory(new PropertyValueFactory<>("nomeSala"));
        affluenzaSalaCol_tableview.setCellValueFactory(new PropertyValueFactory<>("affluenza"));
        ricaviRicavoCol_tableview.setCellValueFactory(new PropertyValueFactory<>("ricavi"));
    }

    private void caricaRegistroBiglietti() throws IOException, ClassNotFoundException {
        String filePath = "registroBiglietti.ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            registroBiglietti = (AbstractRegistroBiglietti) ois.readObject();
        }

        if (registroBiglietti == null) {
            System.out.println("Errore nel caricamento del registro dei biglietti.");
            // Inizializzazione di fallback non necessaria qui, gestita in initialize()
        }
    }

    @FXML
    private void onGeneraReportRicaviClicked() throws FilmGiaPresenteException, DurataFilmNonValidaException, SpettacoloNonTrovatoException, TitoloVuotoException, SalaGiaEsistenteException, SalaNonTrovataException, FilmNonValidoException, SovrapposizioneSpettacoloException, SalaNonValidaException, IOException, FilmNonTrovatoException {
        // Esecuzione dei comandi per la generazione dei report
        ICommand comandoRicavi = new GeneraReportRicaviCommand(ricaviReport);
        ICommand comandoAffluenza = new GeneraReportRicaviCommand(affluenzaReport);

        amministratore.setCommand(comandoRicavi);
        amministratore.eseguiComando();
        amministratore.setCommand(comandoAffluenza);
        amministratore.eseguiComando();

        // Aggiornamento dell'UI con i risultati dei report generati
        aggiornaUIDatiReport();
    }

    private void aggiornaUIDatiReport() {
        // Questo metodo aggrega i dati da entrambi i report e aggiorna l'UI
        Map<Integer, Double> ricaviPerSala = ricaviReport.getRicaviPerSala();
        Map<Integer, Integer> affluenzaPerSala = affluenzaReport.getAffluenzaPerSala();

        List<DatiSala> dati = new ArrayList<>();
        ricaviPerSala.forEach((numeroSala, ricavi) -> {
            Integer affluenza = affluenzaPerSala.getOrDefault(numeroSala, 0);
            String nomeSala = "Sala " + numeroSala;
            dati.add(new DatiSala(nomeSala, affluenza, ricavi));
        });

        ricavi_tableview.setItems(FXCollections.observableArrayList(dati));
    }
}
