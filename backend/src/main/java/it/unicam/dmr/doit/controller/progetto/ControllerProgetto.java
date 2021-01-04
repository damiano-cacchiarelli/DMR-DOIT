package it.unicam.dmr.doit.controller.progetto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.Tag;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@RestController
@RequestMapping("/progetto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProgetto {

	@Autowired
	ProgettoService progettoService;
	@Autowired
	IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	@GetMapping("/vetrina")
	public ResponseEntity<List<Progetto>> vetrinaProgetti() {
		return new ResponseEntity<>(progettoService.listaProgetti(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> progetto(@PathVariable("id") int idProgetto) {
		if (!progettoService.existsById(idProgetto)) {
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(progettoService.findById(idProgetto), HttpStatus.OK);
	}

	@PostMapping("/proponi/{idIscritto}")
	public ResponseEntity<Messaggio> proponiProgetto(@PathVariable("idIscritto") String idIscritto,
			@RequestBody ProgettoDto progetto) {
		Proponente proponente = (Proponente) iscrittoService.findByIdentificativo(idIscritto).get().getRuoli().stream()
				.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROPONENTE)).findFirst().get();
		Set<Tag> tags=new HashSet<>();
		progetto.getTags().forEach(t->tags.add(new Tag(t.getNome(), t.getDescrizione())));
		Progetto p = new Progetto(progetto.getNome(), progetto.getObiettivi(), progetto.getRequisiti(), proponente,tags);
		progettoService.salvaProgetto(p);
		return new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}

	@PutMapping("/candidati/{idProgetto}/{idIscritto}")
	public ResponseEntity<Messaggio> candidatiAlProgetto(@PathVariable("idProgetto") int idProgetto,
			@PathVariable("idIscritto") String idIscritto, @RequestBody InvitoDto invitoDto) {
		Progettista p = (Progettista) iscrittoService.findByIdentificativo(idIscritto).get().getRuoli().stream()
				.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROGETTISTA)).findFirst().get();

		try {
			Progetto pr = progettoService.findById(idProgetto).get();
			pr.getGestoreCandidati().aggiungiCandidato(p);
			progettoService.salvaProgetto(pr);
		} catch (ExistingElementException e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (CandidacyStatusException e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		//TODO invia Invito?
		return new ResponseEntity<>(new Messaggio("Candidato inserito"), HttpStatus.OK);
	}

	@GetMapping("/personali/{idIscritto}")
	public ResponseEntity<?> progettiDiUnIscritto(@PathVariable("idIscritto") String idIscritto) {
		Set<Progetto> c = new HashSet<>();
		iscrittoService.findByIdentificativo(idIscritto).get().getRuoli()
				.forEach(r -> c.addAll(r.getProgettiPersonali()));
		return new ResponseEntity<>(c, HttpStatus.OK);
	}

	@GetMapping("/personali/proponente/{idIscritto}")
	public ResponseEntity<?> progettiDiUnProponente(@PathVariable("idIscritto") String idProponente) {

		Proponente p = (Proponente) iscrittoService.findByIdentificativo(idProponente).get().getRuoli().stream()
				.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROPONENTE)).findFirst().get();

		return new ResponseEntity<>(p.getProgettiPersonali(), HttpStatus.OK);
	}

	@GetMapping("/personali/progettista/{idIscritto}")
	public ResponseEntity<?> progettiDiUnProgettista(@PathVariable("idIscritto") String idProgettista) {

		Progettista p = (Progettista) iscrittoService.findByIdentificativo(idProgettista).get().getRuoli().stream()
				.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROGETTISTA)).findFirst().get();

		return new ResponseEntity<>(p.getProgettiPersonali(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}/chiudi_candidature")
	public ResponseEntity<Messaggio> progettiDiUnProgettista(@PathVariable("id") int idProgetto) {
		Progetto pr =progettoService.findById(idProgetto).get();
		pr.getGestoreCandidati().setCandidatureAperte(false);
		progettoService.salvaProgetto(pr);
		return new ResponseEntity<>(new Messaggio("Candidature chiuse"), HttpStatus.OK);
	}

}
