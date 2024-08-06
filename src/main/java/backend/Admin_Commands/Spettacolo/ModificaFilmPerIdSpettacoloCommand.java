package backend.Admin_Commands.Spettacolo;

import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Cinema_Infrastructure.Spettacolo.Gestione_Spettacolo.IModificaSpettacolo;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;

// ModificaFilmPerIdSpettacoloCommand è un ConcreteCommand che implementa ICommand.
// Questo comando si occupa di modificare il film associato a uno spettacolo specifico.
public class ModificaFilmPerIdSpettacoloCommand implements ICommand {

    // Riferimento al Receiver che ha la logica per modificare lo spettacolo.
    private IModificaSpettacolo edit;

    // Il nuovo film da associare allo spettacolo.
    private IFilm film;

    // ID dello spettacolo da modificare.
    private long idSpettacolo;

    // Costruttore che inizializza il comando con il Receiver, l'ID dello spettacolo
    // e il nuovo film da associare.
    public ModificaFilmPerIdSpettacoloCommand(IModificaSpettacolo edit, long idSpettacolo, IFilm film){
        this.edit = edit;
        this.idSpettacolo = idSpettacolo;
        this.film = film;
    }

    // Metodo execute che chiama il Receiver per eseguire l'azione di modifica del film dello spettacolo.
    // Lancia un'eccezione se lo spettacolo con l'ID specificato non viene trovato.
    public void execute() throws SpettacoloNonTrovatoException {
        edit.modificaFilmPerIdSpettacolo(idSpettacolo, film);
    }
}