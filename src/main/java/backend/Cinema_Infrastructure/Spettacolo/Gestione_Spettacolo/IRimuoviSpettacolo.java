package backend.Cinema_Infrastructure.Spettacolo.Gestione_Spettacolo;

import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;

// IRimuoviSpettacolo agisce come 'Receiver' nel pattern Command.
// Questa interfaccia dichiara i metodi che saranno implementati per rimuovere uno Spettacolo
public interface IRimuoviSpettacolo {
    void rimuoviSpettacoloPerId(long idSpettacolo) throws SpettacoloNonTrovatoException;
}