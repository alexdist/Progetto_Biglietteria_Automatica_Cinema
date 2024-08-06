package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Cinema_Infrastructure.Film.IFilm;

import java.util.List;

public interface IFilmDataSerializer {

    public List<IFilm> caricaFilm();
    public void salvaSala(List<IFilm> filmList);
}
