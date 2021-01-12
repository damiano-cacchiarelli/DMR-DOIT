package it.unicam.dmr.doit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Questa interfaccia implementa {@code JpaRepository<I, String>} e permette
 * l'interazione con il database per la gestione di un {@code I}.
 * <br>
 * Vengono definite delle funzioni per una ricerca intelligente sugli {@code I}
 * memorizzati.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Repository
public interface IscrittoRepository<I extends Iscritto> extends JpaRepository<I, String> {

	/**
	 * @return tutti gli iscritti aventi il ruolo definito dal parametro passato
	 */
	@Query("select i from Iscritto i join Ruolo r on r.iscritto=i.identificativo where r.ruolo=?1")
	public List<I> findByRuolo(TipologiaRuolo ruolo);

	/**
	 * @return tutti gli iscritti aventi il ruolo esperto ordinati in modo
	 *         decrescente in base al rango
	 */
	@Query("select i from Iscritto i join Ruolo r on r.iscritto=i.identificativo join Esperto e on r.id=e.id where r.ruolo='ROLE_ESPERTO' order by rango desc")
	public List<I> findEsperti();

	/*
	 * @return tutti gli iscritti aventi il ruolo esperto e che hanno valutato
	 *         almeno un progetto che presenta la lista dei tag passati
	 */
	// @Query("SELECT i FROM Iscritto i JOIN Ruolo r ON r.iscritto =
	// i.identificativo JOIN Valutazione v ON v.id_esperto = r.id JOIN Progetto p ON
	// p.id = v.id_progetto JOIN categorie_progetti cp ON cp.progetto = p.id WHERE
	// r.ruolo = 'ROLE_ESPERTO' AND cp.categoria IN ?1")
	// public List<I> getEspertiConsigliati(Set<Tag> tags);

}
