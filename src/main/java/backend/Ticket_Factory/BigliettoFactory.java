package backend.Ticket_Factory;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.ID_Persistente.IGeneratoreIDPersistente;

// BigliettoFactory agisce come la classe 'Creator' astratta nel pattern Factory Method.
// Definisce il metodo factory astratto 'creaBiglietto(...)' che le sottoclassi concrete dovranno implementare.
public abstract class BigliettoFactory {
    protected IGeneratoreIDPersistente generatoreID;

    public BigliettoFactory(IGeneratoreIDPersistente generatoreID) {
        this.generatoreID = generatoreID;
    }

    // Il metodo factory astratto che deve essere implementato dalle ConcreteFactory per creare oggetti ConcreteProduct.
    public abstract IBiglietto creaBiglietto(ISpettacolo spettacolo, Utente acquirente, double costo) ;
}
