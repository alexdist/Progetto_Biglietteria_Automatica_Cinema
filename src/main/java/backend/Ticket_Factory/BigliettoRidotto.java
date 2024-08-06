package backend.Ticket_Factory;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.ID_Persistente.IGeneratoreIDPersistente;

import java.io.Serializable;
import java.time.LocalDateTime;

// BigliettoRidotto è un altro 'ConcreteProduct'.
public class BigliettoRidotto implements IBiglietto, Serializable {

    private final long id;
    private final LocalDateTime timestampAcquisto;
    private ISpettacolo spettacolo;
    private Utente acquirente;
    private double costo;
    private boolean isValid;

    public BigliettoRidotto(ISpettacolo spettacolo, Utente acquirente, double costo, IGeneratoreIDPersistente generatoreID) {
        this.timestampAcquisto = LocalDateTime.now();
        this.id = generatoreID.generaProssimoId();
        this.spettacolo = spettacolo;
        this.acquirente = acquirente;
        this.costo = costo;
        this.isValid = true;
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
        return "green";
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
