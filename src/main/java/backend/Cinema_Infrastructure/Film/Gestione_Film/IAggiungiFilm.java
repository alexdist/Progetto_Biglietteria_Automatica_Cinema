package backend.Cinema_Infrastructure.Film.Gestione_Film;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Exception.Film.DurataFilmNonValidaException;
import backend.Exception.Film.FilmGiaPresenteException;
import backend.Exception.Film.TitoloVuotoException;

import java.io.IOException;
import java.util.List;

// IAggiungiFilm agisce come 'Receiver' nel pattern Command.
// Questa interfaccia dichiara i metodi che saranno implementati per aggiungere un film
// e recuperare la lista dei film esistenti.
public interface IAggiungiFilm {

    // Metodo per aggiungere un film alla programmazione.
    // Il film da aggiungere è passato come parametro.
    // Può sollevare eccezioni se il film è già presente, se il titolo è vuoto,
    // se la durata non è valida o se si verifica un errore di I/O.
    void aggiungiFilm(IFilm film) throws FilmGiaPresenteException, TitoloVuotoException, DurataFilmNonValidaException, IOException;

    // Metodo per ottenere l'elenco dei film attualmente in programmazione.
    // Restituisce una lista di film, permettendo così di interrogare
    // quali film sono stati precedentemente aggiunti.
    List<IFilm> getFilm();
}
