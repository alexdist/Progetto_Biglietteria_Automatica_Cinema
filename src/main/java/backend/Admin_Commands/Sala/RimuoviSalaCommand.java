package backend.Admin_Commands.Sala;

import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Sala.Gestione_Sala.IRimuoviSala;
import backend.Exception.Film.FilmNonTrovatoException;
import backend.Exception.Sala.SalaNonTrovataException;

// RimuoviSalaCommand è un 'ConcreteCommand' che implementa l'interfaccia ICommand.
// Questa classe definisce l'azione di rimozione di una Sala.
public class RimuoviSalaCommand implements ICommand {

    // Riferimento all'interfaccia del Receiver che sa come eseguire l'azione di rimozione della Sala.
    private IRimuoviSala remove;

    // Identificativo della Sala da rimuovere.
    private long id;

    // Costruttore che inizializza il comando con il Receiver e l'ID della Sala da rimuovere.
    public RimuoviSalaCommand(IRimuoviSala remove, long id){
        this.remove = remove;
        this.id = id;
    }

    // Metodo execute che, quando invocato, chiama il metodo di rimozione della Sala sul Receiver.
    // Questo metodo può lanciare un'eccezione nel caso in cui la Sala con l'ID specificato
    // non venga trovato.
    public void execute() throws FilmNonTrovatoException, SalaNonTrovataException {
        remove.rimuoviSalaPerId(id);
    }
}
