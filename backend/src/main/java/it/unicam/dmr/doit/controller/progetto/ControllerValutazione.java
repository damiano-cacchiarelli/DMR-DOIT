package it.unicam.dmr.doit.controller.progetto;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.ValutazioneService;

/**
 * Resposabilita�:
 * - lista valutazione progetto
 * - ultima valutazione progetto
 */
@RestController
@RequestMapping("/progetto/valutazione")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerValutazione {

	@Autowired
	private ValutazioneService valutazioneService;
	@Autowired
	private ProgettoService progettoService;

	@GetMapping("/{id_progetto}/all")
	public ResponseEntity<?> valutazioniProgetto(@PathVariable("id_progetto") int idProgetto) {
		if(!progettoService.existsById(idProgetto))
			return new ResponseEntity<>("Il progetto non esiste", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(valutazioneService.getAllValutazioni(idProgetto), HttpStatus.OK);
	}

	@GetMapping("/{id_progetto}/last")
	public ResponseEntity<?> ultimaValutazioneProgetto(@PathVariable("id_progetto") int idProgetto) {
		if(!progettoService.existsById(idProgetto))
			return new ResponseEntity<>("Il progetto non esiste", HttpStatus.NOT_FOUND);
		try {
			Valutazione val = progettoService.findById(idProgetto).get().getLastValutazione();
			return new ResponseEntity<>(val, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}
}
