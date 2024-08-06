package org.example.progetto_cinema.Service_Userdashcontroller;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.Ticket_Pricing.IPrezziBiglietto;

public interface IServizioPrezziBiglietto {
    public void applicaStrategieDiPrezzo(ISpettacolo spettacolo);
    public double calcolaPrezzoFinale(ISpettacolo spettacolo, Utente utente, int numeroBiglietti);
    public void resetPrezziBigliettoAiValoriDefault();
    public IPrezziBiglietto getPrezziBiglietto();
}
