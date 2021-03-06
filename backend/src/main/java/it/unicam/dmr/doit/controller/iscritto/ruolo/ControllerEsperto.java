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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneDto;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.progetto.exception.ProjectStatusException;
import it.unicam.dmr.doit.service.iscritto.ruoli.EspertoService;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di: 
 * <ul>
 * <li>Valutare un progetto;</li>
 * <li>Rifiutare la richiesta di valutazione per un progetto;</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/esperto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerEsperto {

	@Autowired
	private EspertoService espertoService;

	@PreAuthorize("hasRole('ESPERTO')")
	@PostMapping("/progetto/valuta/{id_invito}")
	public ResponseEntity<?> valutaProgetto(@PathVariable("id_invito") String idInvito,
			@Valid @RequestBody ValutazioneDto valutazioneDto, BindingResult bindingResult,
			Authentication authentication) throws ProjectStatusException {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			espertoService.valuta(idInvito, valutazioneDto, authentication.getName());
			return Utils.creaMessaggio("Valutazione aggiunta", HttpStatus.OK);
		} catch (NoSuchElementException | ExistingElementException | ProjectStatusException | IllegalStateException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ESPERTO')")
	@PutMapping("/rifiuta_richiesta/{id_invito}")
	public ResponseEntity<?> rifiutaRichiesta(@PathVariable("id_invito") String idInvito, Authentication authentication)
			throws ProjectStatusException {
		try {
			espertoService.rifiuta(idInvito, authentication.getName());
			return new ResponseEntity<>("Richista di valutazione rifiutata", HttpStatus.OK);
		} catch (IllegalStateException | IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
}
