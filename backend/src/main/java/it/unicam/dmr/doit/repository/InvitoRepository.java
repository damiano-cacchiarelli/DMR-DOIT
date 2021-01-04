package it.unicam.dmr.doit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.InvitoId;
import it.unicam.dmr.doit.invito.InvitoId.RuoloSoggetto;

@Repository
public interface InvitoRepository extends JpaRepository<Invito, InvitoId> {

	public boolean existsById(String id);

	public List<Invito> findById(String id);

	@Modifying
	@Query("delete from Invito where id=?1 and soggetto IN (?2)")
	public void deleteByIdAndSoggettoIn(String id, List<RuoloSoggetto> soggetti);
}