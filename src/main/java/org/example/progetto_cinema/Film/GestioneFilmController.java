package org.example.progetto_cinema.Film;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Domain.Amministratore;
import backend.Domain.Ruolo;
import backend.Exception.Film.DurataFilmNonValidaException;
import backend.Exception.Film.FilmGiaPresenteException;
import backend.Exception.Film.TitoloVuotoException;
import backend.ID_Persistente.GeneratoreIDPersistenteFilm;
import backend.ID_Persistente.IGeneratoreIDPersistente;
import backend.Serializzazione.Adaptee.FilmSerializer;
import backend.Serializzazione.Adapter.FilmSerializerAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.progetto_cinema.Film.Service.GestioneFilmService;
import org.example.progetto_cinema.Film.Service.IGestioneFilmService;
import org.example.progetto_cinema.General_Utility_Classes.AlertUtil;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.FilmDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.IFilmDataSerializer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GestioneFilmController implements Initializable {
    @FXML
    private TableColumn<IFilm, Long> IDFilmCol_tableview;

    @FXML
    private TextField IDRimuoviFIlm_textfield;

    @FXML
    private AnchorPane aggiungiFilm_anchorPane;

    @FXML
    private Button aggiungiFilm_btn;

    @FXML
    private ImageView aggiungiFilm_imageview;

    @FXML
    private TableColumn<IFilm, Integer> durataFilmCol_tableview;

    @FXML
    private TextField durataFilm_textfield;

    @FXML
    private Button eliminaFilm_btn;

    @FXML
    private TableView<IFilm> film_tableview;

    @FXML
    private TableColumn<IFilm, String> genereFilmCol_tableview;

    @FXML
    private TextField genereFilm_textfield;

    @FXML
    private Button importaFilmImageview_btn;

    @FXML
    private AnchorPane rimuoviFilm_anchorpane;

    @FXML
    private Button rimuoviFilm_btn;

    @FXML
    private TableColumn<IFilm, String> titoloFilmCol_tableview;

    @FXML
    private TextField titoloFilm_textfield;

    @FXML
    private Button tornaIndietroRimuoviFilm_btn;

    private ObservableList<IFilm> filmObservableList = FXCollections.observableArrayList();

    private List<IFilm> films;

    private IGestioneFilmService gestioneFilmService;

    private IFilmDataSerializer filmDataSerializer;


    @Override
    public void initialize(URL location, ResourceBundle resources){

        filmDataSerializer = new FilmDataSerializer(new FilmSerializerAdapter(new FilmSerializer()));
        try {
            films = filmDataSerializer.caricaFilm();
            filmObservableList.addAll(films);
        } catch (Exception e) {
            System.out.println("Impossibile caricare i film esistenti. " + e.getMessage());
            films = new ArrayList<>();
        }

        IGeneratoreIDPersistente generatoreIDFilm = new GeneratoreIDPersistenteFilm();

        Amministratore amministratore = new Amministratore("Mario","Rossi", Ruolo.AMMINISTRATORE);
        this.gestioneFilmService = new GestioneFilmService(films, generatoreIDFilm, filmDataSerializer, amministratore);

        titoloFilmCol_tableview.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        durataFilmCol_tableview.setCellValueFactory(new PropertyValueFactory<>("durata"));
        genereFilmCol_tableview.setCellValueFactory(new PropertyValueFactory<>("genere"));
        IDFilmCol_tableview.setCellValueFactory(new PropertyValueFactory<>("id"));

        film_tableview.setItems(filmObservableList);

    }



    @FXML
    public void aggiungiFilm() {
        try {
            String titoloFilm = titoloFilm_textfield.getText();
            String genereFilm = genereFilm_textfield.getText();
            int durataFilm = Integer.parseInt(durataFilm_textfield.getText());

            gestioneFilmService.aggiungiFilm(titoloFilm, durataFilm, genereFilm, nuovoFilm -> {
                AlertUtil.showAlert("Operazione Completata","Film aggiunto con successo!");
                aggiungiFilmTable(nuovoFilm);
            });
        } catch (FilmGiaPresenteException e) {
            AlertUtil.showErrorAlert("Il film è già presente: " + e.getMessage());
        } catch (DurataFilmNonValidaException e) {
            AlertUtil.showErrorAlert("Durata del film non valida: " + e.getMessage());
        } catch (TitoloVuotoException e) {
            AlertUtil.showErrorAlert("Il titolo del film non può essere vuoto.");
        } catch (NumberFormatException e) {
            AlertUtil.showErrorAlert("Formato numero non valido. Assicurati che la durata del film sia un numero.");
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Errore imprevisto durante l'aggiunta del film: " + e.getMessage());
        }
    }



    @FXML
    public void rimuoviFilm() {
        try {
            long idFilm = Long.parseLong(IDRimuoviFIlm_textfield.getText());

            gestioneFilmService.rimuoviFilm(idFilm, idFilmRimosso -> {
                AlertUtil.showAlert("Operazione Completata","Film rimosso con successo!");
                rimuoviFilmTable(idFilmRimosso);
            });
        } catch (NumberFormatException e) {
            AlertUtil.showErrorAlert("ID del film non valido.");
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Errore durante la rimozione del film: " + e.getMessage());
        }
    }


    public void aggiungiFilmTable(IFilm nuovoFilm) {
        filmObservableList.add(nuovoFilm);
    }

    public void rimuoviFilmTable(long idSalaDaRimuovere) {
        // Trova la sala con l'ID specificato
        IFilm filmTrovato = null;
        for (IFilm film : filmObservableList) {
            if (film.getId() == idSalaDaRimuovere) {
                filmTrovato = film;
                break;
            }
        }

        // Rimuovi la sala trovata dalla lista osservabile, se presente
        if (filmTrovato != null) {
            filmObservableList.remove(filmTrovato);
        }
    }

    @FXML
    public void switchFilm(ActionEvent event){
        if(event.getSource() == rimuoviFilm_btn){
            aggiungiFilm_anchorPane.setVisible(false);
            rimuoviFilm_anchorpane.setVisible(true);

        }else if(event.getSource() == tornaIndietroRimuoviFilm_btn){
            aggiungiFilm_anchorPane.setVisible(true);
            rimuoviFilm_anchorpane.setVisible(false);
        }
    }
}
