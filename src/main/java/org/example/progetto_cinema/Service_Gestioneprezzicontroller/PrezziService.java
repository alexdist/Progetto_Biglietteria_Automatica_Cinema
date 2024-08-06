package org.example.progetto_cinema.Service_Gestioneprezzicontroller;

import backend.Admin_Commands.Prezzi_Biglietto.ImpostaPrezzoInteroCommand;
import backend.Admin_Commands.Prezzi_Biglietto.ImpostaPrezzoRidottoCommand;
import backend.Admin_Commands.Prezzi_Biglietto.ImpostaSovrapprezzoCommand;
import backend.Admin_Interfaces.ICommand;
import backend.Domain.Amministratore;
import backend.Serializzazione.Adaptee.PrezziBigliettoSerializer;
import backend.Serializzazione.Adapter.PrezziBigliettoSerializerAdapter;
import backend.Ticket_Pricing.IPrezziBiglietto;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.IPrezziDataSerializer;
import org.example.progetto_cinema.General_Utility_Classes.Serializzazione.PrezziDataSerializer;

public class PrezziService implements IPrezziService{
    private IPrezziBiglietto prezziBiglietto;
    private IPrezziDataSerializer prezziDataSerializer;
    private Amministratore amministratore;

    public PrezziService(IPrezziBiglietto prezziBiglietto, Amministratore amministratore) {
        this.prezziBiglietto = prezziBiglietto;
        this.amministratore = amministratore;
        this.prezziDataSerializer = new PrezziDataSerializer(new PrezziBigliettoSerializerAdapter(new PrezziBigliettoSerializer()));
    }

    public void impostaPrezzoIntero(double nuovoPrezzo) throws Exception {
        // Creazione del comando per cambiare il prezzo intero
        ICommand cambiaPrezzoIntero = new ImpostaPrezzoInteroCommand(this.prezziBiglietto, nuovoPrezzo);
        this.amministratore.setCommand(cambiaPrezzoIntero);
        this.amministratore.eseguiComando();
        prezziDataSerializer.salvaPrezzi(prezziBiglietto);

    }

    public void impostaPrezzoRidotto(double nuovoPrezzo) throws Exception {
        //Creazione del comando per cambiare il prezzo ridotto
        ICommand cambiaPrezzoRidotto = new ImpostaPrezzoRidottoCommand(this.prezziBiglietto, nuovoPrezzo);
        this.amministratore.setCommand(cambiaPrezzoRidotto);
        this.amministratore.eseguiComando();
        prezziDataSerializer.salvaPrezzi(prezziBiglietto);
    }

    public void impostaSovrapprezzo(double sovrapprezzo) throws Exception {
        //Creazione del comando per cambiare il sovrapprezzo weekEnd
        ICommand impostaSovrapprezzo = new ImpostaSovrapprezzoCommand(this.prezziBiglietto, sovrapprezzo);
        this.amministratore.setCommand(impostaSovrapprezzo);
        this.amministratore.eseguiComando();
        prezziDataSerializer.salvaPrezzi(prezziBiglietto);
    }

}
