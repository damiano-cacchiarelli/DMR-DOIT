package it.unicam.dmr.doit.controller.iscritto.ruolo;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagListDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.TagService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Responabilita: - proporre progetto - chiusura candidature - invitare
 * progettista (da fare su ControllerInvito) - permette valutazione progetto -
 * passa a fase successiva
 */
@RestController
@RequestMapping("/proponente")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProponente {

	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private TagService tagService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/proponi")
	public ResponseEntity<?> proponiProgetto(@Valid @RequestBody ProgettoDto progetto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);

		Proponente proponente = (Proponente) iscrittoService
				.getRuolo(authentication.getName(), TipologiaRuolo.ROLE_PROPONENTE).get();
		Set<Tag> tags = tagService
				.getTags(progetto.getTags().stream().map(TagDto::getNome).collect(Collectors.toList()));
		Progetto p = new Progetto(progetto.getNome(), progetto.getObiettivi(), progetto.getRequisiti(), proponente,
				tags);
		progettoService.salvaProgetto(p);
		return new ResponseEntity<>(p, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/chiudi_candidature/{id}")
	public ResponseEntity<Messaggio> chiudiCandidature(@PathVariable("id") int idProgetto) {
		if(!progettoService.existsById(idProgetto))
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		
		// Le candidature possono riaprirsi??
		Progetto progetto = progettoService.findById(idProgetto).get();
		try {
			progetto.getGestoreCandidati().setCandidatureAperte(false);
			progettoService.salvaProgetto(progetto);
			return new ResponseEntity<>(new Messaggio("Candidature al progetto chiuse"), HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/fase_successiva/{id}")
	public ResponseEntity<Messaggio> faseSuccessiva(@PathVariable("id") int idProgetto) {
		if(!progettoService.existsById(idProgetto))
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		
		Progetto progetto = progettoService.findById(idProgetto).get();
		try {
			progetto.nextFase();
			progettoService.salvaProgetto(progetto);
			return new ResponseEntity<>(new Messaggio("Progetto nella fase "), HttpStatus.OK);
		} catch (NextFaseException e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/permette_valutazione/{id}")
	public ResponseEntity<Messaggio> permetteValutazione(@PathVariable("id") int idProgetto) {
		//TODO: invia invito sul frontend
		if(!progettoService.existsById(idProgetto))
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		
		Progetto progetto = progettoService.findById(idProgetto).get();
		progetto.setStato(Stato.IN_VALUTAZIONE);
		progettoService.salvaProgetto(progetto);
		return new ResponseEntity<>(new Messaggio("Progetto in valutazione"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/esperti_consigliati")
	public ResponseEntity<?> espertiConsigliati(@Valid @RequestBody TagListDto tags, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(iscrittoService.getEspertiConsigliati(tags.getTags()), HttpStatus.OK);
	}
}
