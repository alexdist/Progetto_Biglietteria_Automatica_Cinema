package org.example.progetto_cinema.Service_Userdashcontroller;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Domain.Utente;
import backend.Ticket_Factory.IBiglietto;

import java.util.List;

public interface IGestoreAcquisti {
    public List<IBiglietto> procediAcquisto(ISpettacolo spettacoloSelezionato, Utente utente, int numeroBiglietti);

}
