package it.unicam.dmr.doit.repository;

import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.utenti.Ente;

/**
 * Questa interfaccia estende l'interfaccia {@code IscrittoRepository<Ente>} e permette
 * l'interazione con il database per la gestione di un {@code Ente}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Repository
public interface EnteRepository extends IscrittoRepository<Ente>{

}