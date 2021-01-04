package it.unicam.dmr.doit.controller.progetto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneProgettistaDto;
import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.progetto.ValutazioneProgettista;
import it.unicam.dmr.doit.progetto.exception.ExistingElementException;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.service.progetto.ValutazioneService;
import it.unicam.dmr.doit.utenti.ruoli.Esperto;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@RestController
@RequestMapping("/valutazione") // TODO da cambiare
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerValutazione {

	@Autowired
	ValutazioneService valutazioneService;
	@Autowired
	ProgettoService progettoService;

	IscrittoService<?, IscrittoRepository<?>> iscrittoService;

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
	
	@GetMapping("/{id_progetto}/valutazione_candidati")
	public ResponseEntity<?> valutazioneCandidati(@PathVariable("id_progetto") int idProgetto) {
		int idValutazione = progettoService.findById(idProgetto).get().getLastValutazioneId();
		if (idValutazione <= 0)
			return new ResponseEntity<>("Nessuna valutazione per questo progetto", HttpStatus.OK);
		return new ResponseEntity<>(valutazioneService.findById(idValutazione), HttpStatus.OK);
	}

	@PostMapping("/{id_iscritto}/aggiungi")
	public ResponseEntity<?> aggiungiValutazione(@PathVariable("id_iscritto") String idEsperto,
			@RequestBody ValutazioneDto valutazioneDto) {
		try {
			Esperto esperto = (Esperto) iscrittoService.findByIdentificativo(idEsperto).get().getRuoli().stream()
					.filter(r -> r.getRuolo().equals(TipologiaRuolo.ROLE_ESPERTO)).findFirst().get();
			Valutazione valutazione = new Valutazione(valutazioneDto.getRecensione(), esperto);

			for (ValutazioneProgettistaDto vpd : valutazioneDto.getValutazioniCandidati()) {
				valutazione.addValutazioneCandidato(
						new ValutazioneProgettista(vpd.getRecensione(), vpd.getIdentificativoProgettista()));
			}

			progettoService.findById(valutazioneDto.getIdProgetto()).get().aggiungiValutazione(valutazione);
			valutazioneService.salvaValutazione(valutazione);
		} catch (IllegalArgumentException | ExistingElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException e) {
			return new ResponseEntity<>("Non hai il ruolo di esperto", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Valutazione aggiunta", HttpStatus.OK);
	}

}
