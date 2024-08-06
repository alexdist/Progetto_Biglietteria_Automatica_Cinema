package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Serializzazione.Target.IDataSerializer;

import java.util.ArrayList;
import java.util.List;

public class SalaDataSerializer implements ISalaDataSerializer{

    private IDataSerializer salaSerializerAdapter;

    public SalaDataSerializer(IDataSerializer salaSerializerAdapter) {
        this.salaSerializerAdapter = salaSerializerAdapter;
    }

    public List<ISala> caricaSala() {
        Object result = salaSerializerAdapter.deserialize("sale.ser");
        if (result instanceof List<?>) {
            return (List<ISala>) result;
        } else {

            return new ArrayList<>();
        }
    }


    public void salvaSala(List<ISala> saleList) {

        salaSerializerAdapter.serialize(saleList, "sale.ser");
    }
}
