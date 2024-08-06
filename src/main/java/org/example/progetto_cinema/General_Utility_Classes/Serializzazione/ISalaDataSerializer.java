package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Cinema_Infrastructure.Sala.ISala;

import java.util.List;

public interface ISalaDataSerializer {
    public List<ISala> caricaSala();
    public void salvaSala(List<ISala> saleList);
}
