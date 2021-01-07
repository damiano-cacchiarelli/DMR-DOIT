package it.unicam.dmr.doit.service.progetto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public boolean existsByNome(String nome) {
		return tagRepository.existsById(nome);
	}

	public Set<Tag> getTags(List<String> tagsName) {
		Set<Tag> tags = new HashSet<>();
		tagsName.forEach(t->{
			if(existsByNome(t))
				tags.add(findById(t));
		});
		return tags;
	}
}
