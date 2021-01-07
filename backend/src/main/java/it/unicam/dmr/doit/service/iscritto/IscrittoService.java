package it.unicam.dmr.doit.service.iscritto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

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

	public boolean existsByIdentificativo(String identificativo) {
		return iscrittoRepository.existsById(identificativo);
	}

	public void salva(I iscritto) {
		iscrittoRepository.save(iscritto);
	}

	public void elimina(String identificativo) {
		iscrittoRepository.deleteById(identificativo);
	}

	public List<TipologiaRuolo> getRuoliDisponibili(String identificativo) {
		if(!existsByIdentificativo(identificativo))
			throw new NoSuchElementException("Identificativo inesistente.");
		I iscritto = findByIdentificativo(identificativo).get();
		List<TipologiaRuolo> ruoliDisponibili = new ArrayList<>(iscritto.getTipoRuoliPossibili());
		ruoliDisponibili.removeAll(iscritto.getTipologiaRuoli());
		return ruoliDisponibili;
	}
	
	public Optional<Ruolo> getRuolo(String identificativo, TipologiaRuolo ruolo) {
		if(!existsByIdentificativo(identificativo))
			throw new NoSuchElementException("Identificativo inesistente.");
		I iscritto = findByIdentificativo(identificativo).get();
		return iscritto.getRuoli().stream().filter(r -> r.getRuolo().equals(ruolo)).findFirst();
	}

	public List<I> getEspertiConsigliati(Progetto progetto) {
		return iscrittoRepository.findEsperti();
	}
}
