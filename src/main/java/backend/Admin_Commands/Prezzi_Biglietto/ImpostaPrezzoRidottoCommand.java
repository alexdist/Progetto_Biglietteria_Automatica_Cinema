package backend.Admin_Commands.Prezzi_Biglietto;

import backend.Admin_Interfaces.ICommand;
import backend.Ticket_Pricing.IPrezziBiglietto;

// ImpostaPrezzoRidottoCommand è un 'ConcreteCommand' che implementa l'interfaccia ICommand.
// Questa classe definisce interagisce con IPrezziBiglietto per settare il prezzo dei Biglietti.
public class ImpostaPrezzoRidottoCommand implements ICommand {

    // Interfaccia per la gestione dei prezzi dei biglietti.
    private IPrezziBiglietto impostaRidotto;

    // Prezzo ridotto da impostare.
    private double prezzoRidotto;

    public ImpostaPrezzoRidottoCommand(IPrezziBiglietto impostaRidotto, double prezzoRidotto){
        this.impostaRidotto = impostaRidotto;
        this.prezzoRidotto = prezzoRidotto;
    }


    // Metodo che esegue l'azione del comando, impostando il nuovo prezzo ridotto.
    public void execute(){
        impostaRidotto.setPrezzoRidotto(prezzoRidotto);
    }
}
