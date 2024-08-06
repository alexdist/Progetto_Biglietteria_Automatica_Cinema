package backend.Domain;

import backend.Admin_Interfaces.ICommand;
import backend.Exception.Film.*;
import backend.Exception.Sala.SalaGiaEsistenteException;
import backend.Exception.Sala.SalaNonTrovataException;
import backend.Exception.Sala.SalaNonValidaException;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;

import java.io.IOException;

// La classe Amministratore agisce come 'Invoker' nel pattern Command (Il Pattern Command utilizzato per i comandi admin).
// È responsabile per l'invocazione del comando, ma non conosce i dettagli dell'esecuzione.
public class Amministratore extends Persona {

    // Variabile privata per memorizzare il riferimento al comando che deve essere eseguito.
    private ICommand command;

    // Costruttore che inizializza l'Amministratore con il nome, cognome e ruolo,
    // sfruttando anche il costruttore della superclasse Persona.
    public Amministratore(String nome, String cognome, Ruolo ruolo) {
        super(nome, cognome, ruolo);

    }

    // Metodo per impostare il comando specifico da eseguire.
    // Il comando viene passato dall'esterno, permettendo flessibilità.
    public void setCommand(ICommand command){
        this.command = command;
    }

    // Metodo per eseguire il comando.
    // Quando chiamato, questo metodo invocherà il metodo execute del comando impostato.
    // Gestisce una serie di eccezioni che possono essere lanciate durante l'esecuzione del comando.
    public void eseguiComando() throws FilmGiaPresenteException, DurataFilmNonValidaException, TitoloVuotoException, FilmNonTrovatoException, SalaGiaEsistenteException, SalaNonTrovataException, SovrapposizioneSpettacoloException, FilmNonValidoException, SalaNonValidaException, SpettacoloNonTrovatoException, IOException {
        command.execute();
    }

}
