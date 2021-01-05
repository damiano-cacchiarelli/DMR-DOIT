package it.unicam.dmr.doit.controller.iscritto.ruolo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

/**
 * Responsabilità: 
 * - valuta progetto
 */
@RestController
@RequestMapping("/esperto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerEsperto {
	
	@Autowired
	private ValutazioneService valutazioneService;
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private IscrittoService<?, IscrittoRepository<?>> iscrittoService;
	
	@PreAuthorize("hasRole('ESPERTO')")
	@PostMapping("/progetto/valuta")
	public ResponseEntity<?> valutaProgetto(@RequestBody ValutazioneDto valutazioneDto) {
		try {
			Esperto esperto = (Esperto) iscrittoService.findByIdentificativo(valutazioneDto.getIdIscritto()).get().getRuoli().stream()
					.filter(r -> r.getRuolo().equals(TipologiaRuolo.ROLE_ESPERTO)).findFirst().get();
			Valutazione valutazione = new Valutazione(valutazioneDto.getRecensione(), esperto);

			for (ValutazioneProgettistaDto vpd : valutazioneDto.getValutazioniCandidati()) {
				valutazione.addValutazioneCandidato(
						new ValutazioneProgettista(vpd.getRecensione(), vpd.getIdentificativoProgettista()));
			}
			
			// TODO: da controllare se aggiorna il progetto
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
