package backend.Cinema_Infrastructure.Spettacolo.Gestione_Spettacolo;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Exception.Film.FilmNonValidoException;
import backend.Exception.Sala.SalaNonValidaException;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;

import java.io.IOException;
import java.util.List;

// IAggiungiSpettacolo agisce come 'Receiver' nel pattern Command.
// Questa interfaccia dichiara i metodi che saranno implementati per aggiungere uno Spettacolo
// e recuperare la lista degli Spettacoli esistenti.
public interface IAggiungiSpettacolo {
    void aggiungiSpettacolo(ISpettacolo nuovoSpettacolo) throws SovrapposizioneSpettacoloException, FilmNonValidoException, SalaNonValidaException, IOException;
    List<ISpettacolo> getSpettacoli();
}