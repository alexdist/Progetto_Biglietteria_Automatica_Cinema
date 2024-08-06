package backend.User_Interfaces;

import backend.Ticket_Factory.IBiglietto;

// Questa interfaccia rappresenta un Receiver nel pattern Command
public interface AcquistoBiglietto {
    public boolean acquistaBiglietto(IBiglietto biglietto);
}
