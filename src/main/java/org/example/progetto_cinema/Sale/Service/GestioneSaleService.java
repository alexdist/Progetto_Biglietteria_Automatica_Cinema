package org.example.progetto_cinema.Sale.Service;

import backend.Admin_Commands.Sala.AggiungiSalaCommand;
import backend.Admin_Commands.Sala.RimuoviSalaCommand;
import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Sala.Gestione_Sala.AggiungiSala;
import backend.Cinema_Infrastructure.Sala.Gestione_Sala.IAggiungiSala;
import backend.Cinema_Infrastructure.Sala.Gestione_Sala.IRimuoviSala;
import backend.Cinema_Infrastructure.Sala.Gestione_Sala.RimuoviSala;
import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Sala.Sala;
import backend.Domain.Amministratore;
import backend.Domain.Ruolo;
import backend.Exception.Film.*;
import backend.Exception.Sala.*;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;
import backend.ID_Persistente.IGeneratoreIDPersistente;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISalaDataSerializer;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class GestioneSaleService implements IGestioneSaleService {
    private List<ISala> sale;
    private IGeneratoreIDPersistente generatoreID;
    private ISalaDataSerializer salaSerializer;
    private Amministratore amministratore;

    public GestioneSaleService(List<ISala> sale, IGeneratoreIDPersistente generatoreID, ISalaDataSerializer salaSerializer, Amministratore amministratore) {
        this.sale = sale;
        this.generatoreID = generatoreID;
        this.salaSerializer = salaSerializer;
        this.amministratore = amministratore;
    }

    public void aggiungiSala(int numeroSala, int capacita, Consumer<ISala> onSuccess) throws NumeroSalaNegativoException, NumeroPostiNegativoException, SalaGiaEsistenteException, FilmGiaPresenteException, DurataFilmNonValidaException, SpettacoloNonTrovatoException, TitoloVuotoException, SalaNonTrovataException, FilmNonValidoException, SovrapposizioneSpettacoloException, SalaNonValidaException, FilmNonTrovatoException, IOException {
        if (numeroSala <= 0) {
            throw new NumeroSalaNegativoException("Il numero della sala deve essere maggiore di zero.");
        }
        if (capacita <= 0) {
            throw new NumeroPostiNegativoException("La capacità della sala deve essere maggiore di zero.");
        }

        boolean salaEsistente = sale.stream().anyMatch(s -> s.getNumeroSala() == numeroSala);
        if (salaEsistente) {
            throw new SalaGiaEsistenteException("Una sala con questo numero esiste già.");
        }

        IAggiungiSala servizioAggiungiSala = new AggiungiSala(sale, generatoreID);

        ISala nuovaSala = new Sala(numeroSala, capacita);
        ICommand aggiungiSalaCommand = new AggiungiSalaCommand(servizioAggiungiSala, nuovaSala);


        amministratore.setCommand(aggiungiSalaCommand);
        amministratore.eseguiComando();

        onSuccess.accept(nuovaSala);

        salaSerializer.salvaSala(sale);
    }


    public void rimuoviSala(long idSala, Consumer<Long> onSuccess) throws SalaNonTrovataException, Exception {

        try {
            IRimuoviSala servizioRimuoviSala = new RimuoviSala(sale);
            ICommand rimuoviSalaCommand = new RimuoviSalaCommand(servizioRimuoviSala, idSala);

            Amministratore amministratore = new Amministratore("Nome", "Cognome", Ruolo.AMMINISTRATORE);
            amministratore.setCommand(rimuoviSalaCommand);
            amministratore.eseguiComando();

            salaSerializer.salvaSala(sale);

            onSuccess.accept(idSala); // Esegue l'operazione di successo
        } catch (SalaNonTrovataException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Errore imprevisto durante la rimozione della sala.", e);
        }
    }
}
