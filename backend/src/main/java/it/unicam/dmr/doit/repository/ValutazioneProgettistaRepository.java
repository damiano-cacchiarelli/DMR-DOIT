package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.dmr.doit.progetto.ValutazioneProgettista;

/**
 * Questa interfaccia implementa {@code JpaRepository<ValutazioneProgettista, String>} e permette
 * l'interazione con il database per la gestione di un {@code ValutazioneProgettista}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface ValutazioneProgettistaRepository extends JpaRepository<ValutazioneProgettista, String> {

}
