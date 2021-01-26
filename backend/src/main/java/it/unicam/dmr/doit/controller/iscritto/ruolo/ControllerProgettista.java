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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.service.iscritto.ruoli.ProgettistaService;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Candidare un progetista ad un progetto;</li>
 * <li>Gestire le poposte di partecipazione ad un progetto;</li>
 * </ul>
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
	private ProgettistaService progettistaService;

	@PreAuthorize("hasRole('PROGETTISTA')")
	@PutMapping("/candidati")
	public ResponseEntity<Messaggio> candidatiAlProgetto(@Valid @RequestBody InvitoDto invitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			progettistaService.candidatiAlProgetto(authentication.getName(), invitoDto);
			return Utils.creaMessaggio("Candidatura effettuata", HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		} catch (ExistingElementException | CandidacyStatusException | IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('PROGETTISTA')")
	@PutMapping("/gestisci_proposta_partecipazione")
	public ResponseEntity<Messaggio> gestisciPropostaPartecipazione(
			@Valid @RequestBody RispostaInvitoDto rispostaInvitoDto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			progettistaService.gestisciPropostaPartecipazione(rispostaInvitoDto, authentication.getName());

			return Utils.creaMessaggio("L'invito e' stato " + rispostaInvitoDto.getRisposta().toString().toLowerCase(),
					HttpStatus.OK);
		} catch (IllegalStateException | IllegalArgumentException | NoSuchElementException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
}
