package it.unicam.dmr.doit.controller.progetto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.ValutazioneService;

/**
 * Resposabilità:
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
		return new ResponseEntity<>(valutazioneService.getAllValutazioni(idProgetto), HttpStatus.OK);
	}

	@GetMapping("/{id_progetto}/last")
	public ResponseEntity<?> ultimaValutazioneProgetto(@PathVariable("id_progetto") int idProgetto) {
		int idValutazione = progettoService.findById(idProgetto).get().getLastValutazioneId();
		if (idValutazione <= 0)
			return new ResponseEntity<>("Nessuna valutazione per questo progetto", HttpStatus.OK);
		return new ResponseEntity<>(valutazioneService.findById(idValutazione), HttpStatus.OK);
	}
}
