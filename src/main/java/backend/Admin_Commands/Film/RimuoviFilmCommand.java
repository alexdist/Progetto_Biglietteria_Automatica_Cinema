package backend.Admin_Commands.Film;

import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Film.Gestione_Film.IRimuoviFilm;
import backend.Exception.Film.FilmNonTrovatoException;

// RimuoviFilmCommand è un 'ConcreteCommand' che implementa l'interfaccia ICommand.
// Questa classe definisce l'azione di rimozione di un film dalla programmazione.
public class RimuoviFilmCommand implements ICommand {

    // Riferimento all'interfaccia del Receiver che sa come eseguire l'azione di rimozione del film.
    private IRimuoviFilm remove;

    // Identificativo del film da rimuovere.
    private long id;

    // Costruttore che inizializza il comando con il Receiver e l'ID del film da rimuovere.
    public RimuoviFilmCommand(IRimuoviFilm remove, long id){
        this.remove = remove;
        this.id = id;
    }

    // Metodo execute che, quando invocato, chiama il metodo di rimozione del film sul Receiver.
    // Questo metodo può lanciare un'eccezione nel caso in cui il film con l'ID specificato
    // non venga trovato.
    @Override
    public void execute() throws FilmNonTrovatoException {
        remove.rimuoviFilmPerId(id);
    }
}