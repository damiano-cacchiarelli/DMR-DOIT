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

import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
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
		ResponseEntity<?> res = super.canCreate(enteDto, bindingResult);
		if (res.getStatusCode() != HttpStatus.CREATED)
			return res;

		Ente ente = new Ente(enteDto.getIdentificativo(), enteDto.getEmail(),
				passwordEncoder.encode(enteDto.getPassword()), enteDto.getSede(), enteDto.getAnnoDiFondazione());
		iscrittoService.salva(ente);

		return res;
	}
}
