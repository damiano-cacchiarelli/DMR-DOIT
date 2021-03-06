package it.unicam.dmr.doit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.InvitoId;
import it.unicam.dmr.doit.invito.RuoloSoggetto;

/**
 * Questa interfaccia implementa {@code JpaRepository<Invito, InvitoId>} e permette
 * l'interazione con il database per la gestione di un {@code Invito}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Repository
public interface InvitoRepository extends JpaRepository<Invito, InvitoId> {

	public boolean existsById(String id);

	public List<Optional<Invito>> findById(String id);

	@Modifying
	@Query("delete from Invito where id=?1 and soggetto IN (?2)")
	public void deleteByIdAndSoggettoIn(String id, List<RuoloSoggetto> soggetti);
}