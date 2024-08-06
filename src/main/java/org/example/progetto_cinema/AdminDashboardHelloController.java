package org.example.progetto_cinema;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Serializzazione.Adaptee.FilmSerializer;
import backend.Serializzazione.Adaptee.SalaSerializer;
import backend.Serializzazione.Adaptee.SpettacoloSerializer;
import backend.Serializzazione.Adapter.FilmSerializerAdapter;
import backend.Serializzazione.Adapter.SalaSerializerAdapter;
import backend.Serializzazione.Adapter.SpettacoloSerializerAdapter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboardHelloController implements Initializable {

    @FXML
    private Label dashboard_filmInProgTotali;

    @FXML
    private Label dashboard_saleTotali;

    @FXML
    private Label dashboard_spettInPalTotali;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int numeroSaleAttive = getNumeroSaleAttive();
        int numeroFilminProgrammazione = getNumeroFilmInProgrammazione();
        int numeroSpettacoliInPalinsesto = getNumeroSpettacoliInPalinsesto();
        dashboard_saleTotali.setText(String.valueOf(numeroSaleAttive));
        dashboard_filmInProgTotali.setText((String.valueOf(numeroFilminProgrammazione)));
        dashboard_spettInPalTotali.setText(String.valueOf(numeroSpettacoliInPalinsesto));
    }


    private int getNumeroSpettacoliInPalinsesto() {
        ISpettacoloDataSerializer spettacoloDataSerializer = new SpettacoloDataSerializer(new SpettacoloSerializerAdapter(new SpettacoloSerializer()));
        try {
            List<ISpettacolo> spettacoli = spettacoloDataSerializer.caricaSpettacoli();
            return spettacoli.size();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private int getNumeroFilmInProgrammazione(){
        IFilmDataSerializer filmDataSerializer = new FilmDataSerializer(new FilmSerializerAdapter(new FilmSerializer()));
        try{
            List<IFilm> films = filmDataSerializer.caricaFilm();
            return films.size();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    private int getNumeroSaleAttive() {
        ISalaDataSerializer salaDataSerializer = new SalaDataSerializer(new SalaSerializerAdapter(new SalaSerializer()));
        try {

            List<ISala> sale =salaDataSerializer.caricaSala();

            // Il numero di sale attive corrisponde alla dimensione della lista
            return sale.size();
        } catch (Exception e) {

            e.printStackTrace();
            return 0;
        }
    }
}
