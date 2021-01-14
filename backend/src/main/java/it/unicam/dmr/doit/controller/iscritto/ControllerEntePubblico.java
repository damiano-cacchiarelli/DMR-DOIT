package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.EnteRepository;
import it.unicam.dmr.doit.service.iscritto.EnteService;
import it.unicam.dmr.doit.utenti.Ente;

/**
 * Questo controller estende {@code ControllerVisitatore} secondo i tipo
 * parametrici{@code Ente, EnteRepository, EnteService}, ed ha la
 * responsabilita' di registrare un utente come {@code Ente}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/visitatore/ente")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerEntePubblico extends ControllerVisitatore<Ente, EnteRepository, EnteService> {

	@PostMapping("/crea")
	protected ResponseEntity<?> crea(@Valid @RequestBody EnteDto enteDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		
		try {
			iscrittoService.registra(enteDto);
			return Utils.creaRisposta("Registrazione avvenuta con successo.", HttpStatus.CREATED);
		} catch (ExistingElementException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}
	}
}
