package it.unicam.dmr.doit.repository;

import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.utenti.Persona;

/**
 * Questa interfaccia estende l'interfaccia {@code IscrittoRepository<Persona>} e permette
 * l'interazione con il database per la gestione di un {@code Persona}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Repository
public interface PersonaRepository extends IscrittoRepository<Persona>{

}