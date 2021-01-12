package it.unicam.dmr.doit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.progetto.Progetto;

/**
 * Questa interfaccia implementa {@code JpaRepository<Progetto, Integer>} e permette
 * l'interazione con il database per la gestione di un {@code Progetto}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Integer> {

	public List<Progetto> findByNomeContainingIgnoreCase(String nome);
}
