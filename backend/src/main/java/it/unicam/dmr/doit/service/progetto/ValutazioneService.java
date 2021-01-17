package it.unicam.dmr.doit.service.progetto;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneProgettistaDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.progetto.ValutazioneProgettista;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.progetto.exception.ProjectStatusException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.repository.ProgettoRepository;
import it.unicam.dmr.doit.repository.ValutazioneRepository;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Esperto;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

/**
 * Questa classe inietta ({@code @Autowired}) {@code ValutazioneRepository} ed
 * ha la responsabilita' di effettuare le operazioni di modifica, inserimento,
 * ricerca ed eliminazione di una {@code Valutazione}.
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

	@Autowired
	private ProgettoRepository progettoRepository;

	@Autowired
	private IscrittoRepository<Iscritto> iscrittoRepository;

	//@Autowired
	//private InvitoRepository invitoRepository;

	public void valuta(String idInvito, ValutazioneDto valutazioneDto, String identificativo)
			throws ExistingElementException, NotFoundException, ProjectStatusException {

		Progetto progetto = progettoRepository.findById(valutazioneDto.getIdProgetto())
				.orElseThrow(() -> new NotFoundException("Progetto non trovato"));
		Esperto esperto = (Esperto) iscrittoRepository.findById(identificativo)
				.orElseThrow(() -> new NotFoundException("Iscritto inesistente")).getRuolo(TipologiaRuolo.ROLE_ESPERTO);
		/*List<Invito> inviti = invitoRepository.findById(idInvito).stream().map(Optional::get)
				.collect(Collectors.toList());
		*/
		if (!progetto.getStato().equals(Stato.IN_VALUTAZIONE))
			new ProjectStatusException("Non e' possibile valutare il progetto");
		/*if (!inviti.stream().allMatch(i -> i.getIdDestinatario().equals(identificativo) && i.getTipologiaRisposta().equals(TipologiaRisposta.IN_ATTESA)))
			new NotFoundException("Invito non trovato");
		 */
		Valutazione valutazione = new Valutazione(valutazioneDto.getRecensione(), esperto, progetto);
		for (ValutazioneProgettistaDto vpd : valutazioneDto.getValutazioniCandidati()) {
			valutazione.addValutazioneCandidato(
					new ValutazioneProgettista(vpd.getRecensione(), vpd.getIdentificativoProgettista()));
		}
		
		progetto.aggiungiValutazione(valutazione);
		progetto.setStato(Stato.VALUTATO);
		valutazioneRepository.save(valutazione);
		progettoRepository.save(progetto);

		//inviti.forEach(i -> i.setTipologiaRisposta(TipologiaRisposta.ACCETTATA));
		//inviti.forEach(i -> invitoRepository.save(i));
	}

	public boolean existsById(int idValutazione) {
		return valutazioneRepository.existsById(idValutazione);
	}

	public Valutazione findById(int idValutazione) throws NotFoundException {
		return valutazioneRepository.findById(idValutazione)
				.orElseThrow(() -> new NotFoundException("Nessuna valutazione trovata"));
	}

	public void salvaValutazione(Valutazione v) {
		valutazioneRepository.save(v);
	}
}
