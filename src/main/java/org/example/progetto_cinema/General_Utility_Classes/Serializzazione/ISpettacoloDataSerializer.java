package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;

import java.util.List;

public interface ISpettacoloDataSerializer {
    public List<ISpettacolo> caricaSpettacoli();
    public void salvaSpettacolo(List<ISpettacolo> spettacoli);
}
