package backend.Cinema_Infrastructure.Spettacolo.Gestione_Spettacolo;

import backend.Cinema_Infrastructure.Film.IFilm;
import backend.Cinema_Infrastructure.Sala.ISala;
import backend.Cinema_Infrastructure.Spettacolo.ISpettacolo;
import backend.Exception.Spettacolo.SpettacoloNonTrovatoException;

import java.time.LocalDateTime;
import java.util.List;

// ModificaSpettacolo è la classe concreta che implementa l'interfaccia IModificaSpettacolo,
// agendo come il 'Receiver' nel pattern Command.
// Ha il compito di gestire la modifica degli Spettacoli.
public class ModificaSpettacolo implements IModificaSpettacolo {

    // Lista di spettacoli che rappresenta l'elenco completo degli eventi programmati.
    private List<ISpettacolo> spettacoli;

    // Costruttore della classe ModificaSpettacolo.
    // Accetta una lista di spettacoli da gestire.
    public ModificaSpettacolo(List<ISpettacolo> spettacoli) {
        this.spettacoli = spettacoli;
    }

    @Override
    public void modificaFilmPerIdSpettacolo(long idSpettacolo, IFilm nuovoFilm) throws SpettacoloNonTrovatoException {
        ISpettacolo spettacolo = esisteSpettacoloPerId(idSpettacolo);
        spettacolo.setFilm(nuovoFilm);
    }

    @Override
    public void modificaSalaPerIdSpettacolo(long idSpettacolo, ISala nuovaSala) throws SpettacoloNonTrovatoException {
        ISpettacolo spettacolo = esisteSpettacoloPerId(idSpettacolo);
        spettacolo.setSala(nuovaSala);
    }

    @Override
    public void modificaOrarioProiezionePerIdSpettacolo(long idSpettacolo, LocalDateTime nuovoOrario) throws SpettacoloNonTrovatoException {
        ISpettacolo spettacolo = esisteSpettacoloPerId(idSpettacolo);
        spettacolo.setOrarioProiezione(nuovoOrario);
    }

    // Metodo privato per verificare l'esistenza di uno spettacolo basato sull'ID fornito.
    // Utilizza lo streaming della lista degli spettacoli per filtrarli secondo l'ID.
    // Se trova lo spettacolo con l'ID corrispondente, lo restituisce.
    // Se non trova nessun spettacolo con quell'ID, lancia una SpettacoloNonTrovatoException.
    private ISpettacolo esisteSpettacoloPerId(long idSpettacolo) throws SpettacoloNonTrovatoException {
        return spettacoli.stream()
                .filter(spettacolo -> spettacolo.getId() == idSpettacolo)
                .findFirst()
                .orElseThrow(() -> new SpettacoloNonTrovatoException("Spettacolo con ID " + idSpettacolo + " non presente nella lista."));
    }
}