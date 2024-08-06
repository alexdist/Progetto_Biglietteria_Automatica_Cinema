package org.example.progetto_cinema.General_Utility_Classes.Serializzazione;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Serializzazione.Target.IDataSerializer;

import java.util.ArrayList;
import java.util.List;

public class FilmDataSerializer implements IFilmDataSerializer{

    private IDataSerializer filmSerializerAdapter;

    public FilmDataSerializer(IDataSerializer filmSerializerAdapter) {
        this.filmSerializerAdapter = filmSerializerAdapter;
    }

    public List<IFilm> caricaFilm() {
        Object result = filmSerializerAdapter.deserialize("film.ser");
        if (result instanceof List<?>) {
            return (List<IFilm>) result;
        } else {

            return new ArrayList<>();
        }
    }

    public void salvaSala(List<IFilm> filmList) {

        filmSerializerAdapter.serialize(filmList, "film.ser");
    }
}
