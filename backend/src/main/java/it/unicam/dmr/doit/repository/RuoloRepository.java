package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.utenti.ruoli.Ruolo;

/**
 * Questa interfaccia implementa {@code JpaRepository<Ruolo, Integer>} e permette
 * l'interazione con il database per la gestione di un {@code Ruolo}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer>{

}

