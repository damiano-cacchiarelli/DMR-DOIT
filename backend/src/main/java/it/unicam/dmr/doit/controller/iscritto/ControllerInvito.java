package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.invito.EliminazioneInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Ottenere una lista di tutti gli inviti relativi ad un Iscritto;</li>
 * <li>Ottenere uno specifico invito;</li>
 * <li>Inviare un invito;</li>
 * <li>Gestire un Invito;</li>
 * <li>Eliminare un Invito;</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/invito")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerInvito {

	@Autowired
	private InvitoService invitoService;

	@GetMapping("/all")
	public ResponseEntity<?> listaInviti(Authentication authentication) {
		return new ResponseEntity<>(invitoService.listaInviti(authentication.getName()), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getInvito(@PathVariable("id") String id, Authentication authentication) {
		try {
			return Utils.creaRisposta(invitoService.getInvito(id, authentication.getName()), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PostMapping("/invia")
	public ResponseEntity<Messaggio> inviaInvito(@Valid @RequestBody InvitoDto invitoDto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.invia(invitoDto, authentication.getName());
			return Utils.creaMessaggio("Invito inviato!", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@DeleteMapping("/elimina")
	public ResponseEntity<Messaggio> eliminaInvito(@Valid @RequestBody EliminazioneInvitoDto eliminazioneInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.elimina(eliminazioneInvitoDto, authentication.getName());
			return Utils.creaMessaggio("Invito eliminato!", HttpStatus.OK);
		} catch (IllegalStateException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
/*
	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PutMapping("/gestisci")
	public ResponseEntity<Messaggio> gestisciInvito(@Valid @RequestBody RispostaInvitoDto rispostaInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.gestisci(rispostaInvitoDto, authentication.getName());
			return Utils.creaMessaggio("L'invito e' stato " + rispostaInvitoDto.getRisposta().toString().toLowerCase(),
					HttpStatus.OK);
		} catch (IllegalStateException | IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
*/
}
