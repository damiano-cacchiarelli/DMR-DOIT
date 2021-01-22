package it.unicam.dmr.doit.controller.progetto;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagListDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Ricercare un progetto tramite id;</li>
 * <li>Ricercare un progetto tramite nome;</li>
 * <li>Ottenere la lista di tutti i progetti;</li>
 * <li>Ottenere i progetti relativi ad un iscritto (o un ruolo);</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/progetto")
@CrossOrigin(origins = "*")
public class ControllerProgetto {

	@Autowired
	private ProgettoService progettoService;

	@GetMapping("/vetrina")
	public ResponseEntity<List<Progetto>> vetrinaProgetti() {
		return Utils.creaRisposta(progettoService.listaProgetti(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> progetto(@PathVariable("id") int idProgetto) {
		try {
			return Utils.creaRisposta(progettoService.findById(idProgetto), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/ricerca/{nome}")
	public ResponseEntity<?> ricercaProgettoNome(@PathVariable("nome") String nome, @Valid @RequestBody TagListDto tags,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		return Utils.creaRisposta(progettoService.findByName(nome,tags), HttpStatus.OK);
	}

	@GetMapping("/personali")
	public ResponseEntity<?> progettiPersonali(@Valid @RequestBody List<RuoloDto> ruoli, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);
		
		try {
			return Utils.creaRisposta(progettoService.findProgettiPersonali(authentication.getName(),ruoli), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
	}
}
