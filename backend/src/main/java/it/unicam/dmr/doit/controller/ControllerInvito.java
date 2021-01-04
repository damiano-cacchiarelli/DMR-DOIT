package it.unicam.dmr.doit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.invito.InvitoId.RuoloSoggetto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.InvitoService;
import it.unicam.dmr.doit.service.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;

@RestController
@RequestMapping("/invito")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerInvito {


	@Autowired
	private InvitoService invitoService;
	@Autowired
	private IscrittoService<Iscritto, IscrittoRepository<Iscritto>> iscrittoService;

	@GetMapping("/lista")
	public ResponseEntity<?> listaInviti(@RequestBody String identificativoIscritto) {
		if (!iscrittoService.existsById(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Id iscritto inesistente"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(invitoService.listaInviti(identificativoIscritto), HttpStatus.OK);
	}

	@PostMapping("/invia")
	public ResponseEntity<Messaggio> inviaInvito(@RequestBody InvitoDto invitoDto) {

		if (!iscrittoService.existsById(invitoDto.getIdDestinatario())) {
			return new ResponseEntity<>(new Messaggio("Id destinatario inesistente"), HttpStatus.NOT_FOUND);
		}
		if (!iscrittoService.existsById(invitoDto.getIdMittente())) {
			return new ResponseEntity<>(new Messaggio("Id mittente inesistente"), HttpStatus.NOT_FOUND);
		}
		if (invitoDto.getIdMittente().equals(invitoDto.getIdDestinatario())) {
			return new ResponseEntity<>(new Messaggio("Non è possibile inviare un invito a se stessi."),
					HttpStatus.BAD_REQUEST);
		}

		/*
		 * if(!progettoService.existsById(invitoDto.getProgetto())) { return new
		 * ResponseEntity<> (new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND
		 * ); }
		 */

		Iscritto mittente = iscrittoService.findByIdentificativo(invitoDto.getIdMittente()).get();
		Iscritto destinatario = iscrittoService.findByIdentificativo(invitoDto.getIdDestinatario()).get();
		mittente.getGestoreMessaggi().inviaMessaggio(destinatario, invitoDto.getContenuto(), invitoDto.getProgetto(),
				invitoDto.getTipologiaInvito());
		iscrittoService.save(mittente);
		iscrittoService.save(destinatario);
		return new ResponseEntity<>(new Messaggio("Invito inviato"), HttpStatus.OK);
	}

	@DeleteMapping("/elimina/{idInvito}/{tutti}")
	public ResponseEntity<Messaggio> eliminaInvito(@PathVariable("idInvito") String idInvito,
			@PathVariable("tutti") boolean tutti, @RequestBody String identificativoIscritto) {
		if (!invitoService.esisteInvito(idInvito)) {
			return new ResponseEntity<>(new Messaggio("Id invito inesistente"), HttpStatus.NOT_FOUND);
		}
		if (!iscrittoService.existsById(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Id iscritto inesistente"), HttpStatus.NOT_FOUND);
		}
		Invito i = invitoService.getInvito(idInvito);
		Iscritto mittente = i.getMittente();
		Iscritto destinatario = i.getDestinatario();
		if(!mittente.getIdentificativo().equals(identificativoIscritto) && !destinatario.getIdentificativo().equals(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Impossibile eliminare l'invito (non sei autorizzato)"), HttpStatus.BAD_REQUEST);
		}
		Iscritto iscritto = iscrittoService.findByIdentificativo(identificativoIscritto).get();
		iscritto.getGestoreMessaggi().eliminaMessaggio(idInvito, tutti);
		if(mittente.getIdentificativo().equals(identificativoIscritto)) {
			if(tutti) invitoService.eliminaInvito(idInvito, List.of(RuoloSoggetto.MITTENTE, RuoloSoggetto.DESTINATARIO));
			else invitoService.eliminaInvito(idInvito, List.of(RuoloSoggetto.MITTENTE));
		}else invitoService.eliminaInvito(idInvito, List.of(RuoloSoggetto.DESTINATARIO));
		return new ResponseEntity<>(new Messaggio("Invito eliminato"), HttpStatus.OK);
	}
}
