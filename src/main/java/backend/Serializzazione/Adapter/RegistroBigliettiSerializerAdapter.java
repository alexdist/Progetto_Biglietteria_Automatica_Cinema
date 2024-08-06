package backend.Serializzazione.Adapter;

import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.Serializzazione.Adaptee.RegistroBigliettiSerializer;
import backend.Serializzazione.Target.IDataSerializer;

// RegistroBigliettiSerializerAdapter è l'Adapter che implementa l'interfaccia Target IDataSerializer.
// Questa classe adatta l'interfaccia dell'Adaptee RegistroBigliettiSerializer a quella richiesta dal client.
public class RegistroBigliettiSerializerAdapter implements IDataSerializer {
    private RegistroBigliettiSerializer adaptee;

    public RegistroBigliettiSerializerAdapter(RegistroBigliettiSerializer adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void serialize(Object data, String filePath) {
        if (!(data instanceof AbstractRegistroBiglietti)) {
            throw new IllegalArgumentException("Data type not supported for serialization.");
        }
        adaptee.serializeRegistroBiglietti((AbstractRegistroBiglietti) data, filePath);
    }

    @Override
    public Object deserialize(String filePath) {
        return adaptee.deserializeRegistroBiglietti(filePath);
    }
}