package org.example.progetto_cinema.Spettacoli.Service;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public interface IGestioneSpettacoliService {
    void aggiungiSpettacolo(IFilm filmSelezionato, ISala salaSelezionata, LocalDateTime orarioSelezionato, Consumer<ISpettacolo> onSuccess) throws Exception;
    void rimuoviSpettacolo(long idSpettacolo, Consumer<Long> onSuccess) throws Exception;
    void modificaOrarioSpettacolo(long idSpettacolo, LocalDateTime nuovoOrario, Runnable onSuccess) throws Exception;
    void modificaSalaSpettacolo(long idSpettacolo, ISala nuovaSala, Runnable onSuccess) throws Exception;
    void modificaFilmSpettacolo(long idSpettacolo, IFilm nuovoFilm, Runnable onSuccess) throws Exception;
}
