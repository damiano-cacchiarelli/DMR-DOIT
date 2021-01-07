package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.IscrittoDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;

/**
 * Responsabilità: 
 * - visualizza messaggi
 * - aggiungi ruolo
 * - disconnettiti
 */
@RestController
@RequestMapping("/iscritto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerIscritto<I extends Iscritto, R extends IscrittoRepository<I>, S extends IscrittoService<I, R>> {
	
	@Autowired
	protected S iscrittoService;
	
	@PutMapping("/aggiungi_ruolo")
	public ResponseEntity<?> aggiungiRuolo(@Valid @RequestBody RuoloDto ruolo, BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		I iscritto = iscrittoService.findByIdentificativo(authentication.getName()).get();
		if(iscrittoService.getRuoliDisponibili(authentication.getName()).contains(ruolo.getRuolo())) {
			Ruolo r = Ruolo.create(ruolo.getRuolo());
			iscritto.addRuolo(r);
			iscrittoService.salva(iscritto);
			return new ResponseEntity<>(new Messaggio("Il ruolo e' stato aggiunto!"), HttpStatus.OK);
		}
		else return new ResponseEntity<>(new Messaggio("Il ruolo non e' disponibile."), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/ruoli_disponibili")
	public ResponseEntity<?> ruoliDisponibili(Authentication authentication) {
		return new ResponseEntity<>(iscrittoService.getRuoliDisponibili(authentication.getName()), HttpStatus.OK);
	}
	
	@DeleteMapping("/elimina")
	public ResponseEntity<?> elimina(Authentication authentication){
		iscrittoService.elimina(authentication.getName());
		return new ResponseEntity<>(new Messaggio("L'iscritto e' stato eliminato"), HttpStatus.OK); 
	}
	
	protected ResponseEntity<?> canUpdate(IscrittoDto iscrittoDto, BindingResult bindingResult){
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(new Messaggio("L'iscritto e' stato aggiornato"), HttpStatus.OK); 
	}
}