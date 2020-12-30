package it.unicam.dmr.doit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.invito.Invito;
import it.unicam.dmr.doit.service.InvitoService;
import it.unicam.dmr.doit.service.IscrittoService;

@RestController
@RequestMapping("/invito")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerInvito {

	@Autowired
	private InvitoService is;
	@Autowired
	private IscrittoService<?, ?> iscrittoService;

	@GetMapping("/lista")
	public ResponseEntity<?> listaInviti(@RequestBody String identificativoIscritto) {
		if (!iscrittoService.existsById(identificativoIscritto)) {
			return new ResponseEntity<>(new Messaggio("Id iscritto inesistente"), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(is.listaInviti(identificativoIscritto), HttpStatus.OK);
	}

	@PostMapping("/invia")
	public ResponseEntity<Messaggio> inviaInvito(@RequestBody InvitoDto invitoDto) {

		if (!iscrittoService.existsById(invitoDto.getIdDestinatario())) {
			return new ResponseEntity<>(new Messaggio("Id destinatario inesistente"), HttpStatus.NOT_FOUND);
		}

		if (!iscrittoService.existsById(invitoDto.getIdMittente())) {
			return new ResponseEntity<>(new Messaggio("Id mittente inesistente"), HttpStatus.NOT_FOUND);
		}

		/*
		 * if(!progettoService.existsById(invitoDto.getProgetto())) { return new
		 * ResponseEntity<> (new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND
		 * ); }
		 */

		Invito i = new Invito(invitoDto.getContenuto(), invitoDto.getTipologiaInvito(), invitoDto.getIdDestinatario(),
				invitoDto.getIdMittente(), invitoDto.getProgetto());
		is.salvaInvito(i);
		return new ResponseEntity<>(new Messaggio("Invito inviato"), HttpStatus.OK);
	}
	
	@DeleteMapping("/elimina")
	public ResponseEntity<Messaggio> eliminaInvito (@RequestBody int idInvito) {
		
		if (!is.esisteInvito(idInvito)) {
			return new ResponseEntity<>(new Messaggio("Id Invito inesistente"), HttpStatus.NOT_FOUND);
		}
		
		is.eliminaInvito(idInvito);
		return new ResponseEntity<>(new Messaggio("Invito eliminato"), HttpStatus.OK);
	}
}
