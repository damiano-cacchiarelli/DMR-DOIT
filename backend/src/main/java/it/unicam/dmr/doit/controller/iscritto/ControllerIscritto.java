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
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Aggiungere un ruolo ad un Iscritto;</li>
 * <li>Ottenere i ruoli disponibili di un Iscritto;</li>
 * <li>Eliminare l'iscritto</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 *
 * @param <I> tipo di Iscritto
 * @param <R> tipo di Repository (dipendente da I)
 * @param <S> tipo di Service (dipendente da I e R)
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
			return Utils.creaMessaggioDaErrore(bindingResult);
		try {
			if(iscrittoService.aggiungiRuolo(authentication.getName(), ruolo))
				return new ResponseEntity<>(new Messaggio("Il ruolo e' stato aggiunto!"), HttpStatus.OK);
			return new ResponseEntity<>(new Messaggio("Il ruolo non e' disponibile"), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/ruoli_disponibili")
	public ResponseEntity<?> ruoliDisponibili(Authentication authentication) {
		try {
			return Utils.creaRisposta(iscrittoService.getRuoliDisponibili(authentication.getName()), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/elimina")
	public ResponseEntity<?> elimina(Authentication authentication){
		iscrittoService.elimina(authentication.getName());
		return new ResponseEntity<>(new Messaggio("L'iscritto e' stato eliminato"), HttpStatus.OK); 
	}
}