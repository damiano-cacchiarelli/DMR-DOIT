package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.progetto.Progetto;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Integer> {

	boolean existsByNome(String nome); 
}
