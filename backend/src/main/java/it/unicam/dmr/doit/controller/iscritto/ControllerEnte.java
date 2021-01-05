package it.unicam.dmr.doit.controller.iscritto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PutMapping("/aggiorna/{identificativo}")
	protected ResponseEntity<?> aggiorna(@PathVariable("identificativo") String identificativo,
			@RequestBody EnteDto enteDto) {
		ResponseEntity<?> res = super.canUpdate(identificativo, enteDto);
		if (res.getStatusCode() != HttpStatus.OK)
			return res;

		Ente ente = iscrittoService.findByIdentificativo(identificativo).get();
		ente.setSede(enteDto.getSede());
		ente.setAnnoDiFondazione(enteDto.getAnnoDiFondazione());
		iscrittoService.save(ente);

		return res;
	}
}
