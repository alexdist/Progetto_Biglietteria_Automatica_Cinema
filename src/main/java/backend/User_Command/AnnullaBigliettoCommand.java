package backend.User_Command;

import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.User_Interfaces.IUserCommand;

import java.io.Serializable;

// Questa classe rappresenta un ConcreteCommand nel pattern Command.
// Implementa l'interfaccia Command e invoca l'azione sul Receiver.
public class AnnullaBigliettoCommand implements IUserCommand, Serializable {

    private long id;
    private AbstractRegistroBiglietti annulla; // Riferimento al Receiver.
    public AnnullaBigliettoCommand(long id, AbstractRegistroBiglietti annulla){
        this.id = id;
        this.annulla = annulla;
    }


    public void execute(){
        annulla.annullaAcquisto(id);
    }
}
