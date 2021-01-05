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

import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.repository.EnteRepository;
import it.unicam.dmr.doit.service.iscritto.EnteService;
import it.unicam.dmr.doit.utenti.Ente;

@RestController
@RequestMapping("/iscritto/ente")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerEnte extends ControllerIscritto<Ente, EnteRepository, EnteService> {

	@PutMapping("/aggiorna")
	protected ResponseEntity<?> aggiorna(@Valid @RequestBody EnteDto enteDto, BindingResult bindingResult, Authentication authentication) {
		ResponseEntity<?> res = super.canUpdate(enteDto, bindingResult);
		if (res.getStatusCode() != HttpStatus.OK)
			return res;

		Ente ente = iscrittoService.findByIdentificativo(authentication.getName()).get();
		ente.setSede(enteDto.getSede());
		ente.setAnnoDiFondazione(enteDto.getAnnoDiFondazione());
		iscrittoService.salva(ente);

		return res;
	}
}
