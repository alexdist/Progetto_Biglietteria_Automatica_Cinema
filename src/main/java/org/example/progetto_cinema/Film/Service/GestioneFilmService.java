package org.example.progetto_cinema.Film.Service;

import backend.Admin_Commands.Film.AggiungiFilmCommand;
import backend.Admin_Commands.Film.RimuoviFilmCommand;
import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Film.Film;
import backend.Cinema_Infrastructure.Film.Gestione_Film.AggiungiFilm;
import backend.Cinema_Infrastructure.Film.Gestione_Film.IAggiungiFilm;
import backend.Cinema_Infrastructure.Film.Gestione_Film.IRimuoviFilm;
import backend.Cinema_Infrastructure.Film.Gestione_Film.RimuoviFilm;
import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Domain.Amministratore;
import backend.Exception.Film.DurataFilmNonValidaException;
import backend.Exception.Film.FilmGiaPresenteException;
import backend.Exception.Film.FilmNonTrovatoException;
import backend.Exception.Film.TitoloVuotoException;
import backend.ID_Persistente.IGeneratoreIDPersistente;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.IFilmDataSerializer;

import java.util.List;
import java.util.function.Consumer;

public class GestioneFilmService implements IGestioneFilmService {
    private List<IFilm> films;
    private IGeneratoreIDPersistente generatoreID;
    private IFilmDataSerializer filmSerializer;
    private Amministratore amministratore;

    public GestioneFilmService(List<IFilm> films, IGeneratoreIDPersistente generatoreID, IFilmDataSerializer filmSerializer, Amministratore amministratore) {
        this.films = films;
        this.generatoreID = generatoreID;
        this.filmSerializer = filmSerializer;
        this.amministratore = amministratore;
    }

    @Override
    public void aggiungiFilm(String titolo, int durata, String genere, Consumer<IFilm> onSuccess) throws FilmGiaPresenteException, DurataFilmNonValidaException, TitoloVuotoException, Exception {
        // Implementazione del metodo per aggiungere un film, incluso il controllo di validità e unicità
        IFilm nuovoFilm = new Film(titolo, durata, genere);

        IAggiungiFilm servizioAggiungiFilm = new AggiungiFilm(films, generatoreID);
        ICommand aggiungiFilmCommand = new AggiungiFilmCommand(servizioAggiungiFilm, nuovoFilm);

        amministratore.setCommand(aggiungiFilmCommand);
        amministratore.eseguiComando();

        onSuccess.accept(nuovoFilm);

        filmSerializer.salvaSala(films);
    }

    @Override
    public void rimuoviFilm(long idFilm, Consumer<Long> onSuccess) throws FilmNonTrovatoException, Exception {

        IRimuoviFilm servizioRimuoviFilm = new RimuoviFilm(films);
        ICommand rimuoviFilmCommand = new RimuoviFilmCommand(servizioRimuoviFilm, idFilm);

        amministratore.setCommand(rimuoviFilmCommand);
        amministratore.eseguiComando();


        filmSerializer.salvaSala(films);

        onSuccess.accept(idFilm);

    }
}
