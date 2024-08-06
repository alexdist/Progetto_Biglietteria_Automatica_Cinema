package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Ticket_Pricing.IPrezziBiglietto;

import java.util.Optional;

public interface IPrezziDataSerializer {
    public void salvaPrezzi(IPrezziBiglietto prezziBiglietto);
    public Optional<IPrezziBiglietto> caricaPrezzi();
}
