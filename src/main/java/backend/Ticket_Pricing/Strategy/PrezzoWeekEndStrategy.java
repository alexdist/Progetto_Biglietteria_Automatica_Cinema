package backend.Ticket_Pricing.Strategy;

import backend.Ticket_Pricing.IPrezziBiglietto;

// Strategia concreta per impostare i prezzi dei biglietti per il weekend.
public class PrezzoWeekEndStrategy implements IPrezzoStrategy {

    // fornisce i prezzi dei biglietti.
    private IPrezziBiglietto prezziBiglietto;

    // Percentuale di aumento da applicare nel weekend.
    private double aumentoPercentuale;

    // inizializza la strategia con i prezzi biglietto e una percentuale.
    public PrezzoWeekEndStrategy(IPrezziBiglietto prezziBiglietto, double aumentoPercentuale) {
        this.prezziBiglietto = prezziBiglietto;
        this.aumentoPercentuale = aumentoPercentuale;
    }

    // Costruttore alternativo che utilizza un sovrapprezzo predefinito preso dalla classe prezzi biglietto.
    public PrezzoWeekEndStrategy(IPrezziBiglietto prezziBiglietto) {
        this.prezziBiglietto = prezziBiglietto;
        this.aumentoPercentuale = prezziBiglietto.getSovrapprezzo();
    }

    // Implementazione del metodo execute che aumenta i prezzi per il weekend.
    @Override
    public void execute() {
        // Applica un aumento percentuale ai prezzi per il weekend

        double nuovoPrezzoIntero = prezziBiglietto.getPrezzoIntero() * (1 + aumentoPercentuale/100);
        double nuovoPrezzoRidotto = prezziBiglietto.getPrezzoRidotto() * (1 + aumentoPercentuale/100);

        prezziBiglietto.setPrezzoIntero(nuovoPrezzoIntero);
        prezziBiglietto.setPrezzoRidotto(nuovoPrezzoRidotto);
    }
}
