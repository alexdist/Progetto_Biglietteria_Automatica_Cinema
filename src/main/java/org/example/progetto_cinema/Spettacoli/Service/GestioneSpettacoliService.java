package org.example.progetto_cinema.Spettacoli.Service;

import backend.Admin_Commands.Spettacolo.*;
import backend.Admin_Interfaces.ICommand;
import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Spettacolo.Gestione_Spettacolo.*;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Cinema_Infrastructure.Spettacolo.Spettacolo;
import backend.Domain.Amministratore;
import backend.ID_Persistente.IGeneratoreIDPersistente;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISpettacoloDataSerializer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class GestioneSpettacoliService implements IGestioneSpettacoliService{//

    private List<ISpettacolo> spettacoli;
    private IGeneratoreIDPersistente generatoreID;
    private ISpettacoloDataSerializer spettacoloDataSerializer;
    private Amministratore amministratore;

    public GestioneSpettacoliService(List<ISpettacolo> spettacoli, IGeneratoreIDPersistente generatoreID, ISpettacoloDataSerializer spettacoloDataSerializer, Amministratore amministratore) {
        this.spettacoli = spettacoli;
        this.generatoreID = generatoreID;
        this.spettacoloDataSerializer = spettacoloDataSerializer;
        this.amministratore = amministratore;
    }

    public void aggiungiSpettacolo(IFilm filmSelezionato, ISala salaSelezionata, LocalDateTime orarioSelezionato, Consumer<ISpettacolo> onSuccess) throws Exception {
        IAggiungiSpettacolo servizioAggiungiSpettacolo = new AggiungiSpettacolo(spettacoli, generatoreID);

        // Qui uso clone() per assicurare che ogni spettacolo abbia una propria istanza di sala, poiché ho avuto problemi quando decrementavo il numero di posti a sedere per uno spettacolo. Gli spettacoli che avevano lo stesso numero di sala sortivano lo stesso decremento.
        ISala salaClonata = salaSelezionata.clone();
        ISpettacolo nuovoSpettacolo = new Spettacolo(filmSelezionato, salaClonata, orarioSelezionato);

        ICommand aggiungiSpettacoloCommand = new AggiungiSpettacoloCommand(servizioAggiungiSpettacolo, nuovoSpettacolo);
        amministratore.setCommand(aggiungiSpettacoloCommand);
        amministratore.eseguiComando();
        onSuccess.accept(nuovoSpettacolo);

        spettacoloDataSerializer.salvaSpettacolo(spettacoli);
    }


    public void rimuoviSpettacolo(long idSpettacolo, Consumer<Long> onSuccess) throws Exception {
        IRimuoviSpettacolo servizioRimuoviSpettacolo = new RimuoviSpettacolo(spettacoli);
        ICommand rimuoviSpettacoloCommand = new RimuoviSpettacoloCommand(servizioRimuoviSpettacolo, idSpettacolo);

        amministratore.setCommand(rimuoviSpettacoloCommand);
        amministratore.eseguiComando();

        onSuccess.accept(idSpettacolo); // Notifica il successo dell'operazione

        spettacoloDataSerializer.salvaSpettacolo(spettacoli);
    }

    public void modificaOrarioSpettacolo(long idSpettacolo, LocalDateTime nuovoOrario, Runnable onSuccess) throws Exception {

        IModificaSpettacolo servizioModificaOrarioSpettacolo = new ModificaSpettacolo(spettacoli);

        // Crea il comando di modifica spettacolo
        ICommand modificaOrarioSpettacoloCommand = new ModificaOrarioPerIdSpettacoloCommand(servizioModificaOrarioSpettacolo, idSpettacolo, nuovoOrario);

        amministratore.setCommand(modificaOrarioSpettacoloCommand);
        amministratore.eseguiComando();

        onSuccess.run(); // Esegue l'azione di successo, e l'aggiornamento dell'UI

        spettacoloDataSerializer.salvaSpettacolo(spettacoli);
    }

    public void modificaSalaSpettacolo(long idSpettacolo, ISala nuovaSala, Runnable onSuccess) throws Exception {

        IModificaSpettacolo servizioModificaSpettacolo = new ModificaSpettacolo(spettacoli);
        ICommand modificaSalaSpettacoloCommand = new ModificaSalaPerIdSpettacoloCommand(servizioModificaSpettacolo, idSpettacolo, nuovaSala);

        amministratore.setCommand(modificaSalaSpettacoloCommand);
        amministratore.eseguiComando();

        onSuccess.run();

        spettacoloDataSerializer.salvaSpettacolo(spettacoli);
    }

    public void modificaFilmSpettacolo(long idSpettacolo, IFilm nuovoFilm, Runnable onSuccess) throws Exception {


        IModificaSpettacolo servizioModificaSpettacolo = new ModificaSpettacolo(spettacoli);
        ICommand modificaFilmSpettacoloCommand = new ModificaFilmPerIdSpettacoloCommand(servizioModificaSpettacolo, idSpettacolo, nuovoFilm);

        amministratore.setCommand(modificaFilmSpettacoloCommand);
        amministratore.eseguiComando();

        onSuccess.run();

        spettacoloDataSerializer.salvaSpettacolo(spettacoli);
    }
}
