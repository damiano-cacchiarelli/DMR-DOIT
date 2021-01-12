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

import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
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
		ResponseEntity<?> res = super.canCreate(personaDto, bindingResult);
		if (res.getStatusCode() != HttpStatus.CREATED)
			return res;

		Persona persona = new Persona(personaDto.getIdentificativo(), personaDto.getEmail(),
				passwordEncoder.encode(personaDto.getPassword()), personaDto.getNome(), personaDto.getCognome(),
				personaDto.getCittadinanza(), personaDto.getSesso(), personaDto.getTelefono());
		iscrittoService.salva(persona);

		return res;
	}
}
