package backend.Ticket_Factory;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.ID_Persistente.IGeneratoreIDPersistente;

// BigliettoRidottoFactory è un'altra 'ConcreteFactory' che produce 'BigliettoRidotto', un diverso 'ConcreteProduct'.
public class BigliettoRidottoFactory extends BigliettoFactory {

    public BigliettoRidottoFactory(IGeneratoreIDPersistente generatoreID) {
        super(generatoreID);
    }

    @Override
    public IBiglietto creaBiglietto(ISpettacolo spettacolo, Utente acquirente, double costo) {
        return new BigliettoRidotto(spettacolo, acquirente, costo, super.generatoreID);
    }
}
