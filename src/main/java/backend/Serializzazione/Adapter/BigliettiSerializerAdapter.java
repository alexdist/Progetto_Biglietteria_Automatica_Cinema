package backend.Serializzazione.Adapter;

import backend.Serializzazione.Adaptee.BigliettiSerializer;
import backend.Serializzazione.Target.IDataSerializer;
import backend.Ticket_Factory.IBiglietto;

import java.util.List;

public class BigliettiSerializerAdapter implements IDataSerializer {
    private BigliettiSerializer adaptee;

    public BigliettiSerializerAdapter(BigliettiSerializer adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void serialize(Object data, String filePath){
        if (!(data instanceof List<?>)) {
            throw new IllegalArgumentException("Data type not supported for serialization.");
        }
        adaptee.serializeBigliettiList((List<IBiglietto>) data, filePath);
    }

    @Override
    public Object deserialize(String filePath){
        return adaptee.deserializeBigliettiList(filePath);
    }
}
