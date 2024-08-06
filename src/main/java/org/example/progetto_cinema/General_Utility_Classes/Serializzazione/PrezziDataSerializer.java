package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Serializzazione.Target.IDataSerializer;
import backend.Ticket_Pricing.IPrezziBiglietto;

import java.util.Optional;

public class PrezziDataSerializer implements IPrezziDataSerializer{
    private IDataSerializer prezziSerializerAdapter;

    public PrezziDataSerializer(IDataSerializer prezziSerializerAdapter) {
        this.prezziSerializerAdapter = prezziSerializerAdapter;
    }

    public void salvaPrezzi(IPrezziBiglietto prezziBiglietto) {
        try {
            prezziSerializerAdapter.serialize(prezziBiglietto, "prezziBiglietto.ser");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Optional<IPrezziBiglietto> caricaPrezzi() {
        try {
            Object result = prezziSerializerAdapter.deserialize("prezziBiglietto.ser");
            if (result instanceof IPrezziBiglietto) {
                return Optional.of((IPrezziBiglietto) result);
            } else {
                // Gestire il caso in cui il risultato non sia del tipo atteso
                return Optional.empty();
            }
        } catch (Exception e) {

            e.printStackTrace();
            return Optional.empty();
        }
    }
}
