package it.unicam.dmr.doit.service.progetto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.repository.TagRepository;

@Service
@Transactional
public class TagService {
	
	@Autowired
	TagRepository tagRepository;
	
	public Tag findById(String name) {
		return tagRepository.findById(name).get();
	}
	
	public List<Tag> findAllTag(){
		return tagRepository.findAll();
	}
}
