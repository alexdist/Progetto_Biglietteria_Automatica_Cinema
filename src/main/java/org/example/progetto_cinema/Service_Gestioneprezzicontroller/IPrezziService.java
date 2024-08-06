package org.example.progetto_cinema.Service_Gestioneprezzicontroller;

public interface IPrezziService {
    public void impostaPrezzoIntero(double nuovoPrezzo) throws Exception;
    public void impostaPrezzoRidotto(double nuovoPrezzo) throws Exception;
    public void impostaSovrapprezzo(double sovrapprezzo) throws Exception;
}
