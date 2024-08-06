package backend.Ticket_Pricing.Strategy;

import backend.Ticket_Pricing.IPrezziBiglietto;

// Strategia concreta per impostare un prezzo base per i biglietti.
public class PrezzoBaseStrategy implements IPrezzoStrategy {

    // l'oggetto prezziBiglietto fornisce i prezzi dei biglietti.
    private IPrezziBiglietto prezziBiglietto;

    public PrezzoBaseStrategy(IPrezziBiglietto prezziBiglietto) {
        this.prezziBiglietto = prezziBiglietto;
    }

    // Implementazione del metodo execute che imposta i prezzi base dei biglietti.
    @Override
    public void execute() {

        prezziBiglietto.setPrezzoIntero(prezziBiglietto.getPrezzoIntero());
        prezziBiglietto.setPrezzoRidotto(prezziBiglietto.getPrezzoRidotto());
    }
}
