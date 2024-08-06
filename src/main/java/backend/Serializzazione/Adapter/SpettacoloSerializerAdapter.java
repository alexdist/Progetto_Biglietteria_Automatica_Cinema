package backend.Serializzazione.Adapter;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Serializzazione.Adaptee.SpettacoloSerializer;
import backend.Serializzazione.Target.IDataSerializer;

import java.util.List;

// SpettacoloSerializerAdapter è l'Adapter che implementa l'interfaccia Target IDataSerializer.
// Questa classe adatta l'interfaccia dell'Adaptee SpettacoloSerializer a quella richiesta dal client.
public class SpettacoloSerializerAdapter implements IDataSerializer {
    private SpettacoloSerializer adaptee;

    public SpettacoloSerializerAdapter() {
        this.adaptee = new SpettacoloSerializer();
    }

    public SpettacoloSerializerAdapter(SpettacoloSerializer adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void serialize(Object data, String filePath) {
        adaptee.serializeSpettacoloList((List<ISpettacolo>) data, filePath);
    }

    @Override
    public Object deserialize(String filePath) {
        return adaptee.deserializeSpettacoloList(filePath);
    }
}