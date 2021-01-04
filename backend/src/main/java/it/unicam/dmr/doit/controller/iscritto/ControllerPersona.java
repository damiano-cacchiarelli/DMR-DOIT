package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.repository.PersonaRepository;
import it.unicam.dmr.doit.service.iscritto.PersonaService;
import it.unicam.dmr.doit.utenti.Persona;

@RestController
@RequestMapping("/public/iscritto/persona")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerPersona extends ControllerIscritto<Persona, PersonaRepository, PersonaService> {

	@PostMapping("/crea")
	protected ResponseEntity<?> crea(@Valid @RequestBody PersonaDto personaDto, BindingResult bindingResult) {
		ResponseEntity<?> res = super.canCreate(personaDto, bindingResult);
		if (res.getStatusCode() != HttpStatus.CREATED)
			return res;

		Persona persona = new Persona(personaDto.getIdentificativo(), personaDto.getEmail(),
				passwordEncoder.encode(personaDto.getPassword()), personaDto.getNome(), personaDto.getCognome(),
				personaDto.getCittadinanza(), personaDto.getSesso(), personaDto.getTelefono());
		iscrittoService.save(persona);

		return res;
	}

	@PutMapping("/aggiorna/{identificativo}")
	protected ResponseEntity<?> aggiorna(@PathVariable("identificativo") String identificativo,
			@RequestBody PersonaDto personaDto) {
		ResponseEntity<?> res = super.canUpdate(identificativo, personaDto);
		if (res.getStatusCode() != HttpStatus.OK)
			return res;

		Persona persona = iscrittoService.findByIdentificativo(identificativo).get();
		persona.setNome(personaDto.getNome());
		persona.setCognome(personaDto.getCognome());
		persona.setCittadinanza(personaDto.getCittadinanza());
		persona.setSesso(personaDto.getSesso());
		persona.setTelefono(personaDto.getTelefono());
		iscrittoService.save(persona);

		return res;
	}
}
