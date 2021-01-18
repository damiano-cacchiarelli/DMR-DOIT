package it.unicam.dmr.doit.controller.iscritto.ruolo;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di Candidare un progetista ad un
 * progetto.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/progettista")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProgettista {

	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private InvitoService invitoService;

	@PreAuthorize("hasRole('PROGETTISTA')")
	@PutMapping("/candidati/{id_progetto}")
	public ResponseEntity<Messaggio> candidatiAlProgetto(@PathVariable("id_progetto") int idProgetto,
			Authentication authentication) {
		try {
			progettoService.candidatiAlProgetto(authentication.getName(), idProgetto);
			return Utils.creaMessaggio("Candidatura effettuata", HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		} catch (ExistingElementException | CandidacyStatusException | IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('PROGETTISTA')")
	@PutMapping("/gestisci_richiesta_partecipazione")
	public ResponseEntity<Messaggio> gestisciRichiestePartecipazione(
			@Valid @RequestBody RispostaInvitoDto rispostaInvitoDto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.gestisciRichiestePartecipazione(rispostaInvitoDto, authentication.getName());

			return Utils.creaMessaggio("L'invito e' stato " + rispostaInvitoDto.getRisposta().toString().toLowerCase(),
					HttpStatus.OK);
		} catch (IllegalStateException | IllegalArgumentException | ExistingElementException
				| CandidacyStatusException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}catch (NoSuchElementException e) {
			return Utils.creaMessaggio(e, HttpStatus.CONFLICT);
		}
	}
}
