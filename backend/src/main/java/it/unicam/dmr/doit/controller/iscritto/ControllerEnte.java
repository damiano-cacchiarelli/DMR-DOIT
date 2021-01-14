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
import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.repository.EnteRepository;
import it.unicam.dmr.doit.service.iscritto.EnteService;
import it.unicam.dmr.doit.utenti.Ente;
import javassist.NotFoundException;

/**
 * Questo controller estende {@code ControllerVisitatore} secondo i tipo
 * parametrici{@code Ente, EnteRepository, EnteService}, ed ha la
 * responsabilita' di aggiornare i dati aziendali della classe {@code Ente}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/iscritto/ente")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerEnte extends ControllerIscritto<Ente, EnteRepository, EnteService> {

	@PutMapping("/aggiorna")
	protected ResponseEntity<?> aggiorna(@Valid @RequestBody EnteDto enteDto, BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			return Utils.creaRisposta(iscrittoService.aggiorna(authentication.getName(), enteDto), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
		/*
		ResponseEntity<?> res = super.canUpdate(enteDto, bindingResult);
		if (res.getStatusCode() != HttpStatus.OK)
			return res;

		Ente ente = iscrittoService.findByIdentificativo(authentication.getName()).get();
		ente.setSede(enteDto.getSede());
		ente.setAnnoDiFondazione(enteDto.getAnnoDiFondazione());
		iscrittoService.salva(ente);

		return res;
		*/
	}
}
