package it.unicam.dmr.doit.service.progetto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.repository.ProgettoRepository;

@Service
@Transactional
public class ProgettoService {
	
	@Autowired
	ProgettoRepository progettoRepository;
	
	public List<Progetto> listaProgetti(){
		return progettoRepository.findAll();
	}

	public void salvaProgetto(Progetto p) {
		progettoRepository.save(p);
	}

	public boolean existsById(int idProgetto) {
		return progettoRepository.existsById(idProgetto);
	}
	
	public Optional<Progetto> findById(int idProgetto) {
		return progettoRepository.findById(idProgetto);
	}
	
	public List<Progetto> findByName(String nome){
		return progettoRepository.findByNomeContainingIgnoreCase(nome);
	}
	
	
	
	
}
