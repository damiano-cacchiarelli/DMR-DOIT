package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.invito.Invito;

@Repository
public interface InvitoRepository extends JpaRepository<Invito, Integer> {

}
