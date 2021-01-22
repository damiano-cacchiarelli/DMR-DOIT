package it.unicam.dmr.doit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

	@Modifying
	@Query(value = "DELETE FROM progetto_candidati_al_progetto c where c.progetto_id=?1 and c.partecipanti_al_progetto_id=?2", nativeQuery = true)
	public void deleteProgettistaCandidato(int idProgetto, int idProgettista);
	
	@Modifying
	@Query(value = "DELETE FROM progetto_partecipanti_al_progetto c where c.progetto_id=?1 and c.partecipanti_al_progetto_id=?2", nativeQuery = true)
	public void deleteProgettistaPartecipante(int idProgetto, int idProgettista);
}
