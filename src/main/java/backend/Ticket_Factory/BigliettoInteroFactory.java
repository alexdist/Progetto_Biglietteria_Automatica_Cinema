package backend.Ticket_Factory;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.ID_Persistente.IGeneratoreIDPersistente;

// BigliettoInteroFactory è una 'ConcreteFactory'. Estende BigliettoFactory e implementa il metodo factory
// per produrre un oggetto 'BigliettoIntero', che è un 'ConcreteProduct'.
public class BigliettoInteroFactory extends BigliettoFactory {

    public BigliettoInteroFactory(IGeneratoreIDPersistente generatoreID) {
        super(generatoreID);
    }

    @Override
    public IBiglietto creaBiglietto(ISpettacolo spettacolo, Utente acquirente, double costo) {
        return new BigliettoIntero(spettacolo, acquirente, costo, super.generatoreID);
    }
}
