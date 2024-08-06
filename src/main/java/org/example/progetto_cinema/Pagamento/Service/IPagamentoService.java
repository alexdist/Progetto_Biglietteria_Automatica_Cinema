package org.example.progetto_cinema.Pagamento.Service;

import backend.Domain.Utente;
import backend.Payment_Strategy.IMetodoPagamentoStrategy;
import backend.Ticket_Factory.IBiglietto;

import java.io.IOException;
import java.util.List;

public interface IPagamentoService {
    boolean eseguiPagamento(List<IBiglietto> bigliettiDaAcquistare, IMetodoPagamentoStrategy metodoPagamento) throws IOException, ClassNotFoundException;
    void annullaUltimoAcquisto(List<IBiglietto> bigliettiDaAcquistare, Runnable onAnnullamentoSuccess) throws IOException, ClassNotFoundException;
    void setUtente(Utente utente);
}
