package it.unicam.dmr.doit.controller.iscritto.ruolo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneProgettistaDto;
import it.unicam.dmr.doit.progetto.Progetto;
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
	public ResponseEntity<?> valutaProgetto(@Valid @RequestBody ValutazioneDto valutazioneDto, BindingResult bindingResult, Authentication authentication) {
		
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		
		if(!progettoService.existsById(valutazioneDto.getIdProgetto())) {
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		}
		try {
			Esperto esperto = (Esperto) iscrittoService.getRuolo(authentication.getName(), TipologiaRuolo.ROLE_ESPERTO).get();
			Progetto progetto = progettoService.findById(valutazioneDto.getIdProgetto()).get();
			Valutazione valutazione = new Valutazione(valutazioneDto.getRecensione(), esperto, progetto);

			for (ValutazioneProgettistaDto vpd : valutazioneDto.getValutazioniCandidati()) {
				valutazione.addValutazioneCandidato(
						new ValutazioneProgettista(vpd.getRecensione(), vpd.getIdentificativoProgettista()));
			}
			
			// TODO: da controllare se aggiorna il progetto
			progetto.aggiungiValutazione(valutazione);
			valutazioneService.salvaValutazione(valutazione);
			//progettoService.salvaProgetto(progetto);
		} catch (IllegalArgumentException | ExistingElementException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Valutazione aggiunta", HttpStatus.OK);
	}
}
