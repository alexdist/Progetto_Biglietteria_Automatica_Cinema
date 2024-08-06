package org.example.progetto_cinema.Service_Gestionericavicontroller;

import backend.Domain.Amministratore;
import backend.Revenues_Observer.Observable.AbstractRegistroBiglietti;
import backend.Ticket_Factory.IBiglietto;
import org.example.progetto_cinema.Ricavi.DatiSala;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestioneRicaviService implements IGestioneRicaviService{

    private AbstractRegistroBiglietti registroBiglietti;
    private Amministratore amministratore;

    public GestioneRicaviService() {
        try {
            caricaRegistroBiglietti();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void caricaRegistroBiglietti() throws IOException, ClassNotFoundException {
        String filePath = "registroBiglietti.ser";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            registroBiglietti = (AbstractRegistroBiglietti) ois.readObject();
        }

        if (registroBiglietti == null) {
            System.out.println("Errore nel caricamento del registro dei biglietti.");
        }
    }

    public List<DatiSala> calcolaDatiPerSala() {
        List<DatiSala> datiPerSala = new ArrayList<>();

        if (registroBiglietti == null) {
            System.out.println("Registro biglietti non disponibile.");
            return datiPerSala; // Ritorna lista vuota se il registro non è caricato
        }

        Map<Integer, Integer> affluenzaPerSala = new HashMap<>();
        Map<Integer, Double> ricaviPerSala = new HashMap<>();

        List<IBiglietto> biglietti = registroBiglietti.getBiglietti();
        for (IBiglietto biglietto : biglietti) {
            int numeroSala = biglietto.getSpettacolo().getSala().getNumeroSala();
            affluenzaPerSala.merge(numeroSala, 1, Integer::sum);
            ricaviPerSala.merge(numeroSala, biglietto.getCosto(), Double::sum);
        }

        affluenzaPerSala.forEach((numeroSala, affluenza) -> {
            Double ricavi = ricaviPerSala.getOrDefault(numeroSala, 0.0);
            String nomeSala = "Sala " + numeroSala;
            datiPerSala.add(new DatiSala(nomeSala, affluenza, ricavi));
        });

        return datiPerSala;
    }
}
