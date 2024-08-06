package backend.ID_Persistente;

public interface IGeneratoreIDPersistente {
    long generaProssimoId() ;
    void salvaUltimoIdUsato() ;
    void caricaUltimoIdUsato() ;
}
