package it.unicam.dmr.doit.service.progetto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.repository.TagRepository;
import javassist.NotFoundException;

/**
 * Questa classe inietta ({@code @Autowired}) {@code TagRepository} ed ha la
 * responsabilita' di effettuare le operazioni di modifica, inserimento, ricerca
 * ed eliminazione di un {@code Tag}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	public Tag findById(String name) throws NotFoundException {
		return tagRepository.findById(name).orElseThrow(()->new NotFoundException("Nessun Tag trovato con questo nome"));
	}
	
	public List<Tag> findAllTag(){
		return tagRepository.findAll();
	}

	public boolean existsByNome(String nome) {
		return tagRepository.existsById(nome);
	}

	
	//TODO: da eliminare?
	public Set<Tag> getTags(List<String> tagsName) {
		Set<Tag> tags = new HashSet<>();
		tagsName.forEach(t->{
			if(existsByNome(t))
				try {
					tags.add(findById(t));
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		return tags;
	}
}
