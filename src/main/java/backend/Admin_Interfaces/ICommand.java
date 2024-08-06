package backend.Admin_Interfaces;

// Interfaccia Command definisce l'operazione di esecuzione per tutti i comandi concreti.
// Questa interfaccia è il nucleo del pattern Command, consentendo di incapsulare una richiesta come un oggetto.
// Ogni implementazione concreta di questa interfaccia eseguirà un'azione specifica all'interno del sistema.

import backend.Exception.Film.*;
import backend.Exception.Sala.SalaGiaEsistenteException;
import backend.Exception.Sala.SalaNonTrovataException;
import backend.Exception.Sala.SalaNonValidaException;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;

import java.io.IOException;

public interface ICommand {

    // Metodo execute che ogni comando concreto dovrà implementare.
    // Questo metodo è responsabile dell'esecuzione dell'azione specifica del comando.
    void execute() throws FilmGiaPresenteException, DurataFilmNonValidaException, TitoloVuotoException, FilmNonTrovatoException, SalaGiaEsistenteException, SalaNonTrovataException, SovrapposizioneSpettacoloException, FilmNonValidoException, SalaNonValidaException, SpettacoloNonTrovatoException, IOException;
}
