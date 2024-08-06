package backend.Admin_Commands.Prezzi_Biglietto;

import backend.Admin_Interfaces.ICommand;
import backend.Ticket_Pricing.IPrezziBiglietto;

// ImpostaSovrapprezzoCommand è un 'ConcreteCommand' che implementa l'interfaccia ICommand.
// interagisce con IPrezziBiglietto per settare il sovrapprezzo.
public class ImpostaSovrapprezzoCommand implements ICommand {

    // Interfaccia per la gestione dei prezzi dei biglietti.
    private IPrezziBiglietto impostaSovrapprezzo;

    // Sovrapprezzo da impostare.
    private double sovrapprezzo;

    public ImpostaSovrapprezzoCommand(IPrezziBiglietto impostaSovrapprezzo, double sovrapprezzo){
        this.impostaSovrapprezzo = impostaSovrapprezzo;
        this.sovrapprezzo = sovrapprezzo;
    }


    // Metodo che esegue l'azione del comando, impostando il nuovo sovrapprezzo.
    public void execute(){
        impostaSovrapprezzo.setSovrapprezzo(sovrapprezzo);
    }
}
