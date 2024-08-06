package backend.Cinema_Infrastructure.Film.Gestione_Film;

import backend.Exception.Film.FilmNonTrovatoException;

// IRimuoviFilm agisce come 'Receiver' nel pattern Command.
// Questa interfaccia dichiara i metodi che saranno implementati per rimuovere un film.
public interface IRimuoviFilm {
    void rimuoviFilmPerId(long idFilm) throws FilmNonTrovatoException;
}
