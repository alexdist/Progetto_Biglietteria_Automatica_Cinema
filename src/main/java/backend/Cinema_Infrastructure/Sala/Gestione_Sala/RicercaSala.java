package backend.Cinema_Infrastructure.Sala.Gestione_Sala;

import backend.Cinema_Infrastructure.Sala.ISala;

import java.util.List;

public class RicercaSala {
    public static ISala trovaSalaPerNumeroEId(List<ISala> sale, int numeroSala, long id) {
        for (ISala sala : sale) {
            if (sala.getNumeroSala() == numeroSala && sala.getId() == id) {
                return sala;
            }
        }
        return null;
    }
}
