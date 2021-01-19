package it.unicam.dmr.doit.service.iscritto.ruoli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

@Service
@Transactional
public class ProgettistaService {
	
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private InvitoService invitoService;

	
	public void candidatiAlProgetto(String idIscrittoProgettista, InvitoDto invitoDto)
			throws NotFoundException, ExistingElementException, CandidacyStatusException, IllegalArgumentException {
		Progettista progettista = (Progettista) iscrittoService.getByRuolo(TipologiaRuolo.ROLE_PROGETTISTA, idIscrittoProgettista);
		Progetto progetto = progettoService.findById(invitoDto.getIdProgetto());
		if(progetto.getIdProponente().equals(idIscrittoProgettista))
			throw new IllegalArgumentException("Non puoi candidarti ad un tuo progetto.");
		progetto.getGestoreCandidati().aggiungiCandidato(progettista);
		invitoService.invia(invitoDto, idIscrittoProgettista);
		
		progettoService.salva(progetto);

	}
}
