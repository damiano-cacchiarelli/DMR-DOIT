package it.unicam.dmr.doit.controller.iscritto.ruolo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Responabilità:
 * - proporre progetto
 * - chiusura candidature
 * - invitare progettista (da fare su ControllerInvito)
 * - permette valutazione progetto
 * - passa a fase successiva
 */
@RestController
@RequestMapping("/proponente")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProponente {
	
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/proponi")
	public ResponseEntity<Messaggio> proponiProgetto(@RequestBody ProgettoDto progetto) {
		Proponente proponente = (Proponente) iscrittoService.findByIdentificativo(progetto.getIdIscritto()).get().getRuoli().stream()
				.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROPONENTE)).findFirst().get();
		Set<Tag> tags=new HashSet<>();
		progetto.getTags().forEach(t->tags.add(new Tag(t.getNome(), t.getDescrizione())));
		Progetto p = new Progetto(progetto.getNome(), progetto.getObiettivi(), progetto.getRequisiti(), proponente,tags);
		progettoService.salvaProgetto(p);
		return new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/chiudi_candidature/{id}")
	public ResponseEntity<Messaggio> chiudiCandidature(@PathVariable("id") int idProgetto) {
		return null;//new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/fase_successiva/{id}")
	public ResponseEntity<Messaggio> faseSuccessiva(@PathVariable("id") int idProgetto) {
		return null;//new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/permette_valutazione/{id}")
	public ResponseEntity<Messaggio> permetteValutazione(@PathVariable("id") int idProgetto) {
		//TODO: invia invito su frontend
		return null;//new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}
}
