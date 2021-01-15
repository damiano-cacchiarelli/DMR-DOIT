package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

/**
 * Responsabilità:
 * - autenticazione
 * - registrazione
 */
/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Ottenere un iscritto dato l'identificativo;</li>
 * <li>Autenticare un utente iscritto alla piattaforma;</li>
 * <li>Registrare un nuovo utente;</li>
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
@RequestMapping("/visitatore")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerVisitatore<I extends Iscritto, R extends IscrittoRepository<I>, S extends IscrittoService<I, R>> {

	@Autowired
	protected S iscrittoService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getIscritto(@PathVariable("id") String idIscritto) {
		try {
			return Utils.creaRisposta(iscrittoService.getIscritto(idIscritto), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/accedi")
	public ResponseEntity<?> accedi(@Valid @RequestBody LoginIscritto loginIscritto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		try {
			return Utils.creaRisposta(iscrittoService.accedi(loginIscritto), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaRisposta(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/esiste/{identificativo}/{ruolo}")
	public ResponseEntity<?> esisteIscrittoByRuolo(@PathVariable("identificativo") String id, @PathVariable("ruolo") TipologiaRuolo ruolo) {
		return Utils.creaRisposta(iscrittoService.esisteIscrittoByRuolo(id, ruolo), HttpStatus.OK);
	}
}
