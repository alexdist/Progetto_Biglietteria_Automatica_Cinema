package backend.User_Services;

import backend.Payment_Strategy.PayContext;
import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.Ticket_Factory.IBiglietto;
import backend.User_Interfaces.AcquistoBiglietto;

import java.io.Serializable;

public class ServizioAcquistoBiglietto implements AcquistoBiglietto, Serializable {
    private PayContext pay;
    private AbstractRegistroBiglietti registro;


    public ServizioAcquistoBiglietto(PayContext pay, AbstractRegistroBiglietti registro) {
        this.pay = pay;
        this.registro = registro;
    }



    @Override
    public boolean acquistaBiglietto(IBiglietto biglietto) {

        pay.eseguiPagamento(biglietto.getCosto());
        registro.aggiungiBiglietto(biglietto);

        return true; // Acquisto riuscito
    }


}
