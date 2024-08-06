package backend.Serializzazione.Adaptee;

import backend.Cinema_Infrastructure.Sala.ISala;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

// SalaSerializer è l'Adaptee nel pattern Adapter.
// Contiene la logica di business specifica per la serializzazione e deserializzazione delle liste di sale,
// ma la sua interfaccia non è compatibile con quella richiesta dal client.
public class SalaSerializer {

    // Rimossi i modificatori static dai metodi
    public void serializeSaleList(List<ISala> saleList, String filePath){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(saleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ISala> deserializeSaleList(String filePath)  {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<ISala>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}