package org.example.progetto_cinema.Sale.Service;

import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Exception.Film.*;
import backend.Exception.Sala.*;
import backend.Exception.Spettacolo.SovrapposizioneSpettacoloException;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;

import java.io.IOException;
import java.util.function.Consumer;

public interface IGestioneSaleService {
    void aggiungiSala(int numeroSala, int capacita, Consumer<ISala> onSuccess) throws NumeroSalaNegativoException, NumeroPostiNegativoException, SalaGiaEsistenteException, FilmGiaPresenteException, DurataFilmNonValidaException, SpettacoloNonTrovatoException, TitoloVuotoException, SalaNonTrovataException, FilmNonValidoException, SovrapposizioneSpettacoloException, SalaNonValidaException, FilmNonTrovatoException, IOException;
    void rimuoviSala(long idSala, Consumer<Long> onSuccess) throws SalaNonTrovataException, FilmGiaPresenteException, DurataFilmNonValidaException, SpettacoloNonTrovatoException, TitoloVuotoException, SalaGiaEsistenteException, FilmNonValidoException, SovrapposizioneSpettacoloException, SalaNonValidaException, FilmNonTrovatoException, Exception;
}
