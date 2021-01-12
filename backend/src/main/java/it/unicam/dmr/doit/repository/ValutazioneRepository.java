package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.dmr.doit.progetto.Valutazione;

/**
 * Questa interfaccia implementa {@code JpaRepository<Valutazione, Integer>} e permette
 * l'interazione con il database per la gestione di un {@code Valutazione}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface ValutazioneRepository extends JpaRepository<Valutazione, Integer>{

}
