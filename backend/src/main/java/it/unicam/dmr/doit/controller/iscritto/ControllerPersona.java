package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.repository.PersonaRepository;
import it.unicam.dmr.doit.service.iscritto.PersonaService;
import it.unicam.dmr.doit.utenti.Persona;
import javassist.NotFoundException;

/**
 * Questo controller estende {@code ControllerIscritto} secondo i tipo
 * parametrici{@code Persona, PersonaRepository, PersonaService}, ed ha la
 * responsabilita' di aggiornare i dati personali della classe {@code Persona}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/iscritto/persona")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerPersona extends ControllerIscritto<Persona, PersonaRepository, PersonaService> {

	@PutMapping("/aggiorna")
	protected ResponseEntity<?> aggiorna(@Valid @RequestBody PersonaDto personaDto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			return Utils.creaRisposta(iscrittoService.aggiorna(authentication.getName(), personaDto), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
		/*
		 * iscrittoService.aggiorna(personaDto); ResponseEntity<?> res =
		 * super.canUpdate(personaDto, bindingResult); if (res.getStatusCode() !=
		 * HttpStatus.OK) return res;
		 * 
		 * Persona persona =
		 * iscrittoService.findByIdentificativo(authentication.getName()).get();
		 * persona.setNome(personaDto.getNome());
		 * persona.setCognome(personaDto.getCognome());
		 * persona.setCittadinanza(personaDto.getCittadinanza());
		 * persona.setSesso(personaDto.getSesso());
		 * persona.setTelefono(personaDto.getTelefono());
		 * iscrittoService.salva(persona);
		 * 
		 * return res;
		 */
	}
}
