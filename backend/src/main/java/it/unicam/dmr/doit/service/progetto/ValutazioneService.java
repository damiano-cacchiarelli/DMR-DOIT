package it.unicam.dmr.doit.service.progetto;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.repository.ProgettoRepository;
import it.unicam.dmr.doit.repository.ValutazioneRepository;

@Service
@Transactional
public class ValutazioneService {
	
	@Autowired
	ValutazioneRepository valutazioneRepository;
	@Autowired
	ProgettoRepository progettoRepository;

	public Collection<Valutazione> getAllValutazioni(int idProgetto) {
		return progettoRepository.findById(idProgetto).get().getListaValutazioni();
		//TODO spostare in progetto controller?
	}
	
	public Valutazione findById(int idValutazione) {
		return valutazioneRepository.findById(idValutazione).get();
	}

	public void salvaValutazione(Valutazione v) {
		valutazioneRepository.save(v);
	}
}
