package backend.Admin_Commands.Spettacolo;

import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Spettacolo.Gestione_Spettacolo.IAggiungiSpettacolo;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Exception.Film.FilmNonValidoException;
import backend.Exception.Sala.SalaNonValidaException;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;

import java.io.IOException;

// AggiungiSpettacoloCommand è un 'ConcreteCommand' che implementa l'interfaccia ICommand.
// Questa classe definisce l'azione di aggiunta di uno Spettacolo.
public class AggiungiSpettacoloCommand implements ICommand {

    // Riferimento all'interfaccia del Receiver che sa come eseguire l'azione.
    private IAggiungiSpettacolo add;

    // Lo Spettacolo da aggiungere, rappresentato dall'interfaccia ISpettacolo.
    private ISpettacolo spettacolo;

    // Costruttore che associa il Receiver e lo Spettacolo al comando concreto.
    // Questi parametri sono necessari per l'esecuzione del comando.
    public AggiungiSpettacoloCommand(IAggiungiSpettacolo add, ISpettacolo spettacolo){
        this.add = add;
        this.spettacolo = spettacolo;
    }

    // Metodo execute che, quando chiamato, eseguirà l'azione di aggiungere uno Spettacolo
    // tramite il Receiver. Questo metodo può sollevare eccezioni specifiche
    // relative all'aggiunta di una Spettacolo.
    public void execute() throws SovrapposizioneSpettacoloException, FilmNonValidoException, SalaNonValidaException, IOException {
        add.aggiungiSpettacolo(spettacolo);
    }

}
