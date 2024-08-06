package backend.Revenues_Observer.Concrete_ObservableB;

//CONCRETE OBSERVABLE B

import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.Revenues_Observer.Observer.IReport;
import backend.Ticket_Factory.IBiglietto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Implementazione di un ConcreteObserver che monitora e riporta i ricavi per ciascuna sala.
public class RicaviPerSalaReport implements IReport, Serializable {

    // Lista di biglietti usata per calcolare i ricavi.
    private List<IBiglietto> biglietti;

    // Riferimento all'oggetto Observable (registro) che tiene traccia dei biglietti venduti.
    private AbstractRegistroBiglietti registro;

    // Mappa per mantenere lo stato attuale dei ricavi per sala. Questo è specifico per questo ConcreteObserver.
    private Map<Integer, Double> ricaviPerSala; // Aggiunto per mantenere lo stato attuale dei ricavi per sala

    // Il costruttore che permette di inizializzare l'observer e registrarlo presso l'Observable.
    public RicaviPerSalaReport(AbstractRegistroBiglietti registro) {
        this.registro = registro;
        this.biglietti = registro.getBiglietti(); // Inizializza lo stato attuale basato sull'Observable.
        this.ricaviPerSala = new HashMap<>();
        registro.addObserver(this); // Registrazione presso l'Observable per ricevere aggiornamenti.
    }

    // Metodo richiamato dall'Observable quando si verifica un cambiamento di stato.
    // Aggiorna lo stato interno dell'observer basandosi sullo stato aggiornato dell'Observable.
    @Override
    public void update() {
        this.biglietti = registro.getBiglietti(); // Acquisisce i biglietti aggiornati dall'Observable.
    }

    // Genera il report sui ricavi per sala basandosi sull'ultimo stato dei biglietti.
    @Override
    public void generate() {
        // Pulisce lo stato precedente prima di calcolare di nuovo.
        // Calcola i ricavi per sala iterando sui biglietti e accumulando i costi.
        ricaviPerSala.clear();
        // Map<Integer, Double> ricaviPerSala = new HashMap<>();
        for (IBiglietto biglietto : biglietti) {
            int numeroSala = biglietto.getSpettacolo().getSala().getNumeroSala();
            // Utilizza il metodo merge per aggiornare il totale dei ricavi per sala.
            ricaviPerSala.merge(numeroSala, biglietto.getCosto(), Double::sum);
        }
        System.out.println("Report Ricavi per Sala:");
        //espressione lambda ( ovver è funziona anonima che permette di definire in maniera breve e diretta un azione)
        ricaviPerSala.forEach((sala, ricavo) ->
                System.out.println("Sala " + sala + ": ricavi totali = " + ricavo + "€"));
    }

    // Metodo per ottenere i ricavi per sala. Fornisce una copia della mappa per preservare l'incapsulamento.
    public Map<Integer, Double> getRicaviPerSala() {
        return new HashMap<>(ricaviPerSala);
    }
}
