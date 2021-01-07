package it.unicam.dmr.doit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unicam.dmr.doit.progetto.Tag;

public interface TagRepository extends JpaRepository<Tag, String>{
	
	
}
