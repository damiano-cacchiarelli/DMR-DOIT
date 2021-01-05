package it.unicam.dmr.doit.controller.iscritto.ruolo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.InvitoDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Responsabilità: 
 * - candidarsi
 * - gestire richieste di partecipazione (fatto da ControllerInvito)
 */
@RestController
@RequestMapping("/progettista")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProgettista {
	
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;
	
	@PreAuthorize("hasRole('PROGETTISTA')")
	@PutMapping("/candidati")
	public ResponseEntity<Messaggio> candidatiAlProgetto(@RequestBody InvitoDto invitoDto) {
		Progettista p = (Progettista) iscrittoService.findByIdentificativo(invitoDto.getIdMittente()).get().getRuoli().stream()
				.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROGETTISTA)).findFirst().get();
		try {
			Progetto pr = progettoService.findById(invitoDto.getIdProgetto()).get();
			pr.getGestoreCandidati().aggiungiCandidato(p);
			progettoService.salvaProgetto(pr);
		} catch (ExistingElementException e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (CandidacyStatusException e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		//TODO: invia invito su frontend
		return new ResponseEntity<>(new Messaggio("Candidato inserito"), HttpStatus.OK);
	}
}
