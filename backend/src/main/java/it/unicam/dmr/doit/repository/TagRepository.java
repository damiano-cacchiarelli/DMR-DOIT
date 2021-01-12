package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.dmr.doit.progetto.Tag;

/**
 * Questa interfaccia implementa {@code JpaRepository<Tag, String>} e permette
 * l'interazione con il database per la gestione di un {@code Tag}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
public interface TagRepository extends JpaRepository<Tag, String>{
	
	
}
