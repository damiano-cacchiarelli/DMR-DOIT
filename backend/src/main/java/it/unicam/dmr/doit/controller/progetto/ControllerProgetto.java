package it.unicam.dmr.doit.controller.progetto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagDto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Responabilità:
 * - ricerca progetto (per id e nome)
 * - lista progetti
 */
@RestController
@RequestMapping("/progetto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProgetto {

	@Autowired
	private ProgettoService progettoService;

	@GetMapping("/vetrina")
	public ResponseEntity<List<Progetto>> vetrinaProgetti() {
		return new ResponseEntity<>(progettoService.listaProgetti(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> progetto(@PathVariable("id") int idProgetto) {
		if (!progettoService.existsById(idProgetto)) {
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(progettoService.findById(idProgetto), HttpStatus.OK);
	}
	
	@GetMapping("/ricerca/{nome}")
	public ResponseEntity<?> ricercaProgetto(@PathVariable("nome") String nome, @RequestBody List<TagDto> tags) {
		return null;//new ResponseEntity<>(progettoService.findById(idProgetto), HttpStatus.OK);
	}
	
	// TODO: Lista di ruoli 
	@GetMapping("/personali/{id_iscritto}")
	public ResponseEntity<?> progettiDiUnProponente(@PathVariable("id_iscritto") String idIscritto, List<RuoloDto> ruoli) {

		//Proponente p = (Proponente) iscrittoService.findByIdentificativo(idProponente).get().getRuoli().stream()
		//		.filter(t -> t.getRuolo().equals(TipologiaRuolo.ROLE_PROPONENTE)).findFirst().get();

		return null;//new ResponseEntity<>(p.getProgettiPersonali(), HttpStatus.OK);
	}
}
