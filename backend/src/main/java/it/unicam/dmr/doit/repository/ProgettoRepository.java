package it.unicam.dmr.doit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.progetto.Progetto;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Integer> {

	public List<Progetto> findByNomeContainingIgnoreCase(String nome);
}
