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
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagListDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.progetto.exception.CandidacyStatusException;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.progetto.exception.NextFaseException;
import it.unicam.dmr.doit.progetto.exception.ProjectStatusException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.iscritto.ruoli.ProponenteService;
import it.unicam.dmr.doit.utenti.Iscritto;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Proporre un progetto;</li>
 * <li>Chiudere le candidature ad un progetto;</li>
 * <li>Passare alla fase successiva;</li>
 * <li>Permettere di far valutare il progetto;</li>
 * <li>Ottenere una lista di esperti consigliati relativi al progetto
 * proposto;</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/proponente")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProponente {

	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;
	@Autowired
	private ProponenteService proponenteService;

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/proponi")
	public ResponseEntity<?> proponiProgetto(@Valid @RequestBody ProgettoDto progetto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		try {
			return Utils.creaRisposta(proponenteService.proponi(progetto, authentication.getName()),
					HttpStatus.CREATED);
		} catch (NoSuchElementException | NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/chiudi_candidature/{id}")
	public ResponseEntity<Messaggio> chiudiCandidature(@PathVariable("id") int idProgetto) {
		try {
			proponenteService.chiudiCandidature(idProgetto);
			return Utils.creaMessaggio("Candidature chiuse", HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		} catch (CandidacyStatusException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PutMapping("/fase_successiva/{id}")
	public ResponseEntity<Messaggio> faseSuccessiva(@PathVariable("id") int idProgetto) {
		try {
			Progetto progetto = proponenteService.faseSuccessiva(idProgetto);
			return Utils.creaMessaggio("Progetto nella fase " + progetto.getFase(), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		} catch (NextFaseException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/permetti_valutazione")
	public ResponseEntity<Messaggio> permetteValutazione(@Valid @RequestBody InvitoDto invitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			proponenteService.permetteValutazione(invitoDto, authentication.getName());
			return Utils.creaMessaggio("Progetto in valutazione", HttpStatus.OK);
		} catch (IllegalArgumentException | ProjectStatusException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/invita_candidati")
	public ResponseEntity<Messaggio> invitaCandidati(@Valid @RequestBody InvitoDto invitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		try {
			proponenteService.invitaProgettisti(invitoDto, authentication.getName());
			return Utils.creaMessaggio("Progettisti invitati", HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		} catch (NoSuchElementException | ExistingElementException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/seleziona_candidati")
	public ResponseEntity<Messaggio> selezionaCandidati(@Valid @RequestBody RispostaInvitoDto rispostaInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		try {
			proponenteService.selezionaCandidato(rispostaInvitoDto, authentication.getName());
			return Utils.creaMessaggio("Candidati selezionati", HttpStatus.OK);
		} catch (IllegalStateException | IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('PROPONENTE')")
	@PostMapping("/esperti_consigliati")
	public ResponseEntity<?> espertiConsigliati(@Valid @RequestBody TagListDto tags, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		return new ResponseEntity<>(iscrittoService.getEspertiConsigliati(tags.getTags()), HttpStatus.OK);
	}
}
