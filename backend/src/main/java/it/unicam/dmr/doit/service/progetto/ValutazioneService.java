package it.unicam.dmr.doit.service.progetto;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.repository.ValutazioneRepository;

/**
 * Questa classe inietta ({@code @Autowired}) {@code ValutazioneRepository} ed ha la
 * responsabilita' di effettuare le operazioni di modifica, inserimento, ricerca
 * ed eliminazione di una {@code Valutazione}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class ValutazioneService {
	
	@Autowired
	private ValutazioneRepository valutazioneRepository;
	
	public boolean existsById(int idValutazione) {
		return valutazioneRepository.existsById(idValutazione);
	}

	public Valutazione findById(int idValutazione) {
		return valutazioneRepository.findById(idValutazione).get();
	}

	public void salvaValutazione(Valutazione v) {
		valutazioneRepository.save(v);
	}
}
