package backend.Cinema_Infrastructure.Film.Gestione_Film;

import backend.Cinema_Infrastructure.Film.IFilm;

import java.util.List;

public class RicercaFilm {
    public static IFilm trovaFilmPerTitoloEId(List<IFilm> filmInProgrammazione, String titolo, long id) {
        for (IFilm film : filmInProgrammazione) {
            if (film.getTitolo().equalsIgnoreCase(titolo) && film.getId() == id) {
                return film;
            }
        }
        return null;
    }
}
