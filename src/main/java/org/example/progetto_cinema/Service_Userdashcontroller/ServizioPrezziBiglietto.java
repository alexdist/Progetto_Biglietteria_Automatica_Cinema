package org.example.progetto_cinema.Service_Userdashcontroller;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.Ticket_Pricing.IPrezziBiglietto;
import backend.Ticket_Pricing.PrezziBiglietto;
import backend.Ticket_Pricing.Strategy.Context;
import backend.Ticket_Pricing.Strategy.PrezzoBaseStrategy;
import backend.Ticket_Pricing.Strategy.PrezzoWeekEndStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.DayOfWeek;

public class ServizioPrezziBiglietto implements IServizioPrezziBiglietto{
    private IPrezziBiglietto prezziBiglietto;

    public ServizioPrezziBiglietto() {
        caricaPrezziBiglietti();
    }

    private void caricaPrezziBiglietti() {
        File file = new File("prezziBiglietto.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // Deserializza i prezzi del biglietto dal file
                prezziBiglietto = (IPrezziBiglietto) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                // Gestione dell'errore o inizializzazione a valori di default
                inizializzaPrezziDefault();
            }
        } else {
            // Gestione del caso in cui il file non esiste: inizializza a valori di default
            inizializzaPrezziDefault();
        }
    }

    public void applicaStrategieDiPrezzo(ISpettacolo spettacolo) {
        resetPrezziBigliettoAiValoriDefault();

        if (spettacolo != null) {

            Context context = new Context(new PrezzoBaseStrategy(prezziBiglietto));
            context.executeStrategy();

            DayOfWeek giorno = spettacolo.getOrarioProiezione().getDayOfWeek();
            if (giorno == DayOfWeek.SATURDAY || giorno == DayOfWeek.SUNDAY) {
                context.setStrategy(new PrezzoWeekEndStrategy(prezziBiglietto));
                context.executeStrategy();
            }
        }
    }


    public double calcolaPrezzoFinale(ISpettacolo spettacolo, Utente utente, int numeroBiglietti) {
        applicaStrategieDiPrezzo(spettacolo);

        double prezzoBase = utente.getEta() < 14 ? prezziBiglietto.getPrezzoRidotto() : prezziBiglietto.getPrezzoIntero();
        double prezzoFinale = prezzoBase * numeroBiglietti;
        return prezzoFinale;
    }



    private void inizializzaPrezziDefault() {
        // valori di default
        prezziBiglietto = new PrezziBiglietto(0, 0, 0); // Assumiamo questi come valori di default
    }

    public void resetPrezziBigliettoAiValoriDefault() {
        caricaPrezziBiglietti();
    }


    public IPrezziBiglietto getPrezziBiglietto() {
        return prezziBiglietto;
    }
}
