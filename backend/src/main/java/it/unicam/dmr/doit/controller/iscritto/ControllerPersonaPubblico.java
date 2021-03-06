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
import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.PersonaRepository;
import it.unicam.dmr.doit.service.iscritto.PersonaService;
import it.unicam.dmr.doit.utenti.Persona;

/**
 * Questo controller estende {@code ControllerVisitatore} secondo i tipo
 * parametrici{@code Persona, PersonaRepository, PersonaService}, ed ha la
 * responsabilita' di registrare un utente come {@code Persona}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/visitatore/persona")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerPersonaPubblico extends ControllerVisitatore<Persona, PersonaRepository, PersonaService> {

	@PostMapping("/crea")
	protected ResponseEntity<?> crea(@Valid @RequestBody PersonaDto personaDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		
		try {
			iscrittoService.registra(personaDto);
			return Utils.creaMessaggio("Registrazione avvenuta con successo.", HttpStatus.CREATED);
		} catch (ExistingElementException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}
	}
}
