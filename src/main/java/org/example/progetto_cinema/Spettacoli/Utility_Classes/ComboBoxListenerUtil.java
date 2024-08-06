package org.example.progetto_cinema.Spettacoli.Utility_Classes;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Cinema_Infrastructure.Sala.ISala;
import javafx.scene.control.ComboBox;

public class ComboBoxListenerUtil {
    public static void addFilmSelectionListener(ComboBox<IFilm> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Film selezionato: " + newValue.getTitolo());
            }
        });
    }

    public static void addSalaSelectionListener(ComboBox<ISala> comboBox) {
        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Sala selezionata: " + newValue.getNumeroSala());
            }
        });
    }
}
