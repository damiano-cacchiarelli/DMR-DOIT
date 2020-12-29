package it.unicam.dmr.doit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.utenti.Iscritto;

@Service
@Transactional
public class IscrittoService<I extends Iscritto, R extends IscrittoRepository<I>> {

	@Autowired
	protected R iscrittoRepository;
	
	public List<I> getAll() {
		return iscrittoRepository.findAll();
	}

	public Optional<I> findByIdentificativo(String identificativo) {
		return iscrittoRepository.findById(identificativo);
	}
	
	public boolean existsById(String identificativo) {
		return iscrittoRepository.existsById(identificativo);
	}
	
	public void save(I iscritto) {
		iscrittoRepository.save(iscritto);
	}

	public void delete(String identificativo) {
		iscrittoRepository.deleteById(identificativo);
	}
}
