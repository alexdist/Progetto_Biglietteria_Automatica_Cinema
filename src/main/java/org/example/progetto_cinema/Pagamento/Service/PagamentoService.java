package org.example.progetto_cinema.Pagamento.Service;

import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.Payment_Strategy.IMetodoPagamentoStrategy;
import backend.Payment_Strategy.PayContext;
import backend.Revenues_Observer.Concrete_Observable.RegistroBiglietti;
import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.Serializzazione.Adaptee.RegistroBigliettiSerializer;
import backend.Serializzazione.Adaptee.SalaSerializer;
import backend.Serializzazione.Adaptee.SpettacoloSerializer;
import backend.Serializzazione.Adapter.RegistroBigliettiSerializerAdapter;
import backend.Serializzazione.Adapter.SalaSerializerAdapter;
import backend.Serializzazione.Adapter.SpettacoloSerializerAdapter;
import backend.Serializzazione.Target.IDataSerializer;
import backend.Ticket_Factory.IBiglietto;
import backend.User_Command.AcquistoBigliettoCommand;
import backend.User_Command.AnnullaBigliettoCommand;
import backend.User_Interfaces.AcquistoBiglietto;
import backend.User_Interfaces.IUserCommand;
import backend.User_Services.ServizioAcquistoBiglietto;
import org.example.progetto_cinema.General_Utility_Classes.AlertUtil;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISalaDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.ISpettacoloDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.SalaDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.SpettacoloDataSerializer;

import java.io.IOException;
import java.util.List;

public class PagamentoService implements IPagamentoService {

    private AbstractRegistroBiglietti registroBiglietti;
    private List<ISpettacolo> spettacoli;
    private List<ISala> sale;
    private Utente utente;
    private ISalaDataSerializer salaSerializer;
    private ISpettacoloDataSerializer spettacoloSerializer;


    public PagamentoService(AbstractRegistroBiglietti registroBiglietti, List<ISpettacolo> spettacoli, List<ISala> sale) {

        this.registroBiglietti = registroBiglietti;
        this.salaSerializer = new SalaDataSerializer(new SalaSerializerAdapter(new SalaSerializer()));
        this.spettacoloSerializer = new SpettacoloDataSerializer(new SpettacoloSerializerAdapter(new SpettacoloSerializer()));
        this.spettacoli = spettacoli;
        this.sale = sale;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    @Override
    public boolean eseguiPagamento(List<IBiglietto> bigliettiDaAcquistare, IMetodoPagamentoStrategy metodoPagamento) throws IOException, ClassNotFoundException {
        PayContext pagamento = new PayContext(metodoPagamento);
        AcquistoBiglietto servizioAcquisto = new ServizioAcquistoBiglietto(pagamento, registroBiglietti);

        for (IBiglietto biglietto : bigliettiDaAcquistare) {
            if (aggiornaStatoPostoAcquistato(biglietto.getSpettacolo().getId())) {
                IUserCommand acquistoCommand = new AcquistoBigliettoCommand(servizioAcquisto, biglietto);
                utente.setCommand(acquistoCommand);
                utente.eseguiComando();

                salaSerializer.salvaSala(sale);
                spettacoloSerializer.salvaSpettacolo(spettacoli);

                aggiornaRicaviConNuovoBiglietto(biglietto);
            } else {
                AlertUtil.showInformationAlert("Non è possibile procedere all'acquisto: posti esauriti per lo spettacolo " + biglietto.getSpettacolo().getFilm().getTitolo());
                return false; // Termina la funzione e indica che l'acquisto non è riuscito
            }
        }
        // Se il ciclo termina senza entrare nell'else, significa che tutti gli acquisti sono andati a buon fine
        return true;
    }

    private void aggiornaRicaviConNuovoBiglietto(IBiglietto nuovoBiglietto) throws IOException, ClassNotFoundException {
        // Carica il registro dei biglietti esistente
        IDataSerializer adapter = new RegistroBigliettiSerializerAdapter(new RegistroBigliettiSerializer());
        String filePath = "registroBiglietti.ser";
        AbstractRegistroBiglietti registroEsistente = (AbstractRegistroBiglietti) adapter.deserialize(filePath);

        if (registroEsistente == null) {
            registroEsistente = new RegistroBiglietti(); // O creane uno nuovo se non esiste
        }

        // Aggiungi il nuovo biglietto al registro
        registroEsistente.aggiungiBiglietto(nuovoBiglietto);

        // Serializza il registro aggiornato
        adapter.serialize(registroEsistente, filePath);
    }

    private boolean aggiornaStatoPostoAcquistato(long idSpettacolo) throws IOException {
        // Cerca lo spettacolo tramite ID
        ISpettacolo spettacoloTrovato = spettacoli.stream()
                .filter(s -> s.getId() == idSpettacolo)
                .findFirst()
                .orElse(null);

        if (spettacoloTrovato != null && spettacoloTrovato.getSala().occupaPosto()) {
            // Se il posto è stato occupato con successo, serializza lo stato aggiornato
            salaSerializer.salvaSala(sale);
            spettacoloSerializer.salvaSpettacolo(spettacoli);
            return true; // Posto occupato e stato serializzato con successo
        } else {
            AlertUtil.showErrorAlert("La sala è piena o lo spettacolo non è stato trovato.");
            return false; // Posti esauriti o spettacolo non trovato
        }
    }

    public void annullaUltimoAcquisto(List<IBiglietto> bigliettiDaAcquistare, Runnable onAnnullamentoSuccess) throws IOException, ClassNotFoundException {
        if (bigliettiDaAcquistare.isEmpty()) {
            AlertUtil.showInformationAlert("Annullamento Acquisto. Non ci sono acquisti recenti da annullare.");
            return;
        }

        // Carica il registro dei biglietti esistente
        IDataSerializer adapter = new RegistroBigliettiSerializerAdapter(new RegistroBigliettiSerializer());
        String filePath = "registroBiglietti.ser";
        AbstractRegistroBiglietti registroEsistente = (AbstractRegistroBiglietti) adapter.deserialize(filePath);

        if (registroEsistente == null) {
            AlertUtil.showErrorAlert("Errore nel caricamento del registro dei biglietti.");
            return;
        }

        // Ottiene l'ultimo biglietto acquistato
        IBiglietto ultimoBiglietto = bigliettiDaAcquistare.get(bigliettiDaAcquistare.size() - 1);

        // Crea il comando per annullare l'acquisto del biglietto
        IUserCommand annullaCommand = new AnnullaBigliettoCommand(ultimoBiglietto.getId(), registroEsistente);
        utente.setCommand(annullaCommand);
        utente.eseguiComando();

        if (registroEsistente.isUltimoAnnullamentoRiuscito()) {
            onAnnullamentoSuccess.run(); // Chiama il callback
            // Libera il posto associato all'ultimo biglietto annullato
            if (liberaPostoSpettacolo(ultimoBiglietto.getSpettacolo().getId())) {
                System.out.println("Posto liberato con successo.");
            } else {
                System.out.println("Errore nella liberazione del posto.");
            }
            // Assumendo che l'annullamento sia andato a buon fine, aggiorna il registro
            // e serializza il registro aggiornato
            adapter.serialize(registroEsistente, filePath);

            // Rimuovi l'ultimo biglietto dalla lista dei biglietti acquistati
            bigliettiDaAcquistare.remove(ultimoBiglietto);

            // Mostra un Alert per confermare l'annullamento dell'acquisto
            AlertUtil.showAlert("Annullamento Confermato", "L'ultimo acquisto è stato annullato con successo.");
        } else {
            // Mostra un Alert per l'annullamento fallito
            AlertUtil.showErrorAlert("Annullamento Fallito, Non è stato possibile annullare l'acquisto.");
        }
    }

    private boolean liberaPostoSpettacolo(long idSpettacolo) throws IOException {
        // Cerca lo spettacolo tramite ID
        ISpettacolo spettacoloTrovato = spettacoli.stream()
                .filter(s -> s.getId() == idSpettacolo)
                .findFirst()
                .orElse(null);

        if (spettacoloTrovato != null) {
            // Libera il posto nello spettacolo trovato
            spettacoloTrovato.getSala().liberaPosto();
            // Serializza lo stato aggiornato della sala e degli spettacoli
            salaSerializer.salvaSala(sale);
            spettacoloSerializer.salvaSpettacolo(spettacoli);
            return true; // Posto liberato e stato serializzato con successo
        } else {
            AlertUtil.showAlert("Spettacolo non trovato.", "Lo spettacolo non è stato trovato");
            return false; // Spettacolo non trovato
        }
    }
}
