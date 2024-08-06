package backend.Ticket_Factory;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.ID_Persistente.IGeneratoreIDPersistente;

import java.io.Serializable;
import java.time.LocalDateTime;

// BigliettoIntero è un 'ConcreteProduct' che implementa l'interfaccia 'Product' (IBiglietto).
public class BigliettoIntero implements IBiglietto, Serializable {

    // Dettagli specifici del biglietto intero
    private final long id;
    private ISpettacolo spettacolo;
    private final LocalDateTime timestampAcquisto;

    private Utente acquirente;
    private double costo;
    private boolean isValid;

    // Costruttore che inizializza il biglietto intero
    public BigliettoIntero(ISpettacolo spettacolo, Utente acquirente, double costo, IGeneratoreIDPersistente generatoreID) {
        this.timestampAcquisto = LocalDateTime.now();

        // genera il prossimo ID
        this.id = generatoreID.generaProssimoId();
        this.spettacolo = spettacolo;
        this.acquirente = acquirente;
        this.costo = costo;
        this.isValid = true; // All'inizio, il biglietto è valido
    }

    @Override
    public void invalidate() {
        this.isValid = false;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }

    @Override
    public String getColore() {
        return "blue";
    }

    @Override
    public String getLogoCinema() {
        return "Logo Cinema XYZ";
    }

    @Override
    public ISpettacolo getSpettacolo() {
        return this.spettacolo;
    }

    @Override
    public Utente getAcquirente() {
        return this.acquirente;
    }

    @Override
    public double getCosto() {
        return this.costo;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public LocalDateTime getTimestampAcquisto() {
        return timestampAcquisto;
    }
}
