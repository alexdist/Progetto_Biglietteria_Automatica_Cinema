package org.example.progetto_cinema.Film.Service;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Exception.Film.DurataFilmNonValidaException;
import backend.Exception.Film.FilmGiaPresenteException;
import backend.Exception.Film.FilmNonTrovatoException;
import backend.Exception.Film.TitoloVuotoException;

import java.util.function.Consumer;

public interface IGestioneFilmService {
    void aggiungiFilm(String titolo, int durata, String genere, Consumer<IFilm> onSuccess) throws FilmGiaPresenteException, DurataFilmNonValidaException, TitoloVuotoException, Exception;
    void rimuoviFilm(long idFilm, Consumer<Long> onSuccess) throws FilmNonTrovatoException, Exception;
}
