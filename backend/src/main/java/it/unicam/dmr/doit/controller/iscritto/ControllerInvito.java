package it.unicam.dmr.doit.controller.iscritto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.InvitoDto;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.InvitoId.RuoloSoggetto;
import it.unicam.dmr.doit.progetto.Progetto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.service.progetto.ProgettoService;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Responsabilità: - inviare invito - eliminare invito - lista inviti -
 * accettare/rifiutare invito
 */
@RestController
@RequestMapping("/invito")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerInvito {

	@Autowired
	private InvitoService invitoService;
	@Autowired
	private ProgettoService progettoService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	@GetMapping("/all")
	public ResponseEntity<?> listaInviti(@RequestBody String identificativoIscritto) {
		if (!iscrittoService.existsById(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Id iscritto inesistente"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(invitoService.listaInviti(identificativoIscritto), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PostMapping("/invia")
	public ResponseEntity<Messaggio> inviaInvito(@RequestBody InvitoDto invitoDto) {

		if (!iscrittoService.existsById(invitoDto.getIdDestinatario())) {
			return new ResponseEntity<>(new Messaggio("Id destinatario inesistente"), HttpStatus.NOT_FOUND);
		}
		if (!iscrittoService.existsById(invitoDto.getIdMittente())) {
			return new ResponseEntity<>(new Messaggio("Id mittente inesistente"), HttpStatus.NOT_FOUND);
		}
		if (invitoDto.getIdMittente().equals(invitoDto.getIdDestinatario())) {
			return new ResponseEntity<>(new Messaggio("Non e' possibile inviare un invito a se stessi."),
					HttpStatus.BAD_REQUEST);
		}
		if (!progettoService.existsById(invitoDto.getIdProgetto())) {
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);
		}

		Iscritto mittente = iscrittoService.findByIdentificativo(invitoDto.getIdMittente()).get();
		Iscritto destinatario = iscrittoService.findByIdentificativo(invitoDto.getIdDestinatario()).get();
		Progetto progetto = progettoService.findById(invitoDto.getIdProgetto()).get();
		mittente.getGestoreMessaggi().inviaMessaggio(destinatario, invitoDto.getContenuto(), progetto,
				invitoDto.getTipologiaInvito());
		iscrittoService.save(mittente);
		iscrittoService.save(destinatario);
		return new ResponseEntity<>(new Messaggio("Invito inviato"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	// TODO: da modificare -> eliminare tutti i parametri e rimpiazzarli con un
	// oggetto EliminazioneInvitoDto
	@DeleteMapping("/elimina/{id}/{opzioni}")
	public ResponseEntity<Messaggio> eliminaInvito(@PathVariable("id") String idInvito,
			@PathVariable("opzioni") boolean tutti, @RequestBody String identificativoIscritto) {
		if (!invitoService.esisteInvito(idInvito)) {
			return new ResponseEntity<>(new Messaggio("Id invito inesistente"), HttpStatus.NOT_FOUND);
		}
		if (!iscrittoService.existsById(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Id iscritto inesistente"), HttpStatus.NOT_FOUND);
		}
		Invito i = invitoService.getInvito(idInvito);
		Iscritto mittente = i.getMittente();
		Iscritto destinatario = i.getDestinatario();
		if (!mittente.getIdentificativo().equals(identificativoIscritto)
				&& !destinatario.getIdentificativo().equals(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Impossibile eliminare l'invito (non sei autorizzato)"),
					HttpStatus.BAD_REQUEST);
		}
		Iscritto iscritto = iscrittoService.findByIdentificativo(identificativoIscritto).get();
		iscritto.getGestoreMessaggi().eliminaMessaggio(idInvito, tutti);
		if (mittente.getIdentificativo().equals(identificativoIscritto)) {
			if (tutti)
				invitoService.eliminaInvito(idInvito, List.of(RuoloSoggetto.MITTENTE, RuoloSoggetto.DESTINATARIO));
			else
				invitoService.eliminaInvito(idInvito, List.of(RuoloSoggetto.MITTENTE));
		} else
			invitoService.eliminaInvito(idInvito, List.of(RuoloSoggetto.DESTINATARIO));
		return new ResponseEntity<>(new Messaggio("Invito eliminato"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PutMapping("/gestisci/{id}")
	public ResponseEntity<Messaggio> gestisciInvito(@PathVariable("id") String idInvito) {
		// accettare o rifiutare una richiesta
		return null;// new ResponseEntity<>(new Messaggio("Progetto inserito"), HttpStatus.OK);
	}
}
