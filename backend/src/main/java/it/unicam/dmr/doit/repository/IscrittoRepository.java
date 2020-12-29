package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unicam.dmr.doit.utenti.Iscritto;

@Repository
public interface IscrittoRepository<I extends Iscritto> extends JpaRepository<I, String>{

}

