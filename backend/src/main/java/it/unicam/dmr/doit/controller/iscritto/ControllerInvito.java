package it.unicam.dmr.doit.controller.iscritto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.invito.EliminazioneInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
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
	public ResponseEntity<?> listaInviti(Authentication authentication) {
		return new ResponseEntity<>(invitoService.listaInviti(authentication.getName()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getInvito(@PathVariable("id") String id, Authentication authentication) {
		if(!invitoService.esisteInvito(id))
			return new ResponseEntity<>(new Messaggio("Invito inesistente"), HttpStatus.NOT_FOUND);
		List<Invito> listaInviti = invitoService.listaInviti(authentication.getName());
		Optional<Invito> invito = listaInviti.stream().filter(i -> i.getId().equals(id)).findFirst();
		if(invito.isEmpty())
			return new ResponseEntity<>(new Messaggio("Invito inesistente"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(invito.get(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PostMapping("/invia")
	public ResponseEntity<Messaggio> inviaInvito(@Valid @RequestBody InvitoDto invitoDto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggio(Utils.getErrore(bindingResult), HttpStatus.BAD_REQUEST);
		for (String idDest : invitoDto.getIdDestinatario()) {
			if (!iscrittoService.existsByIdentificativo(idDest))
				return new ResponseEntity<>(new Messaggio("Id destinatario inesistente"), HttpStatus.NOT_FOUND);
		}
		if (!progettoService.existsById(invitoDto.getIdProgetto()))
			return new ResponseEntity<>(new Messaggio("Progetto inesistente"), HttpStatus.NOT_FOUND);

		for (String idDest : invitoDto.getIdDestinatario()) {
			if (authentication.getName().equals(idDest)) continue;
			Iscritto mittente = iscrittoService.findByIdentificativo(authentication.getName()).get();
			Iscritto destinatario = iscrittoService.findByIdentificativo(idDest).get();
			Progetto progetto = progettoService.findById(invitoDto.getIdProgetto()).get();
			mittente.getGestoreMessaggi().inviaMessaggio(destinatario, invitoDto.getContenuto(), progetto,
					invitoDto.getTipologiaInvito());
			iscrittoService.salva(mittente);
			iscrittoService.salva(destinatario);	
		}
		return new ResponseEntity<>(new Messaggio("Invito inviato"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@DeleteMapping("/elimina")
	public ResponseEntity<Messaggio> eliminaInvito(@Valid @RequestBody EliminazioneInvitoDto eliminazioneInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		if (!invitoService.esisteInvito(eliminazioneInvitoDto.getIdInvito()))
			return new ResponseEntity<>(new Messaggio("Id invito inesistente"), HttpStatus.NOT_FOUND);

		Invito i = invitoService.getInvito(eliminazioneInvitoDto.getIdInvito()).get();
		Iscritto mittente = i.getMittente();
		Iscritto destinatario = i.getDestinatario();
		if (!mittente.getIdentificativo().equals(authentication.getName())
				&& !destinatario.getIdentificativo().equals(authentication.getName())) {
			return new ResponseEntity<>(new Messaggio("Impossibile eliminare l'invito (non sei autorizzato)"),
					HttpStatus.BAD_REQUEST);
		}
		Iscritto iscritto = iscrittoService.findByIdentificativo(authentication.getName()).get();
		iscritto.getGestoreMessaggi().eliminaMessaggio(eliminazioneInvitoDto.getIdInvito(),
				eliminazioneInvitoDto.isOpzioni());
		if (mittente.getIdentificativo().equals(authentication.getName())) {
			if (eliminazioneInvitoDto.isOpzioni())
				invitoService.eliminaInvito(eliminazioneInvitoDto.getIdInvito(),
						List.of(RuoloSoggetto.MITTENTE, RuoloSoggetto.DESTINATARIO));
			else
				invitoService.eliminaInvito(eliminazioneInvitoDto.getIdInvito(), List.of(RuoloSoggetto.MITTENTE));
		} else
			invitoService.eliminaInvito(eliminazioneInvitoDto.getIdInvito(), List.of(RuoloSoggetto.DESTINATARIO));
		return new ResponseEntity<>(new Messaggio("Invito eliminato"), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PutMapping("/gestisci")
	public ResponseEntity<Messaggio> gestisciInvito(@Valid @RequestBody RispostaInvitoDto rispostaInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		if (!invitoService.esisteInvito(rispostaInvitoDto.getIdInvito()))
			return new ResponseEntity<>(new Messaggio("Id invito inesistente"), HttpStatus.NOT_FOUND);
		Invito invito = invitoService.getInvito(rispostaInvitoDto.getIdInvito()).get();
		if (!invito.getIdDestinatario().equals(authentication.getName()))
			return new ResponseEntity<>(
					new Messaggio("Non sei autorizzato a rispondere a questo invito (non sei il destinatario)"),
					HttpStatus.BAD_REQUEST);

		List<Invito> inviti = invitoService.getInviti(rispostaInvitoDto.getIdInvito()).stream().map(Optional::get)
				.collect(Collectors.toList());
		try {
			inviti.forEach(i -> i.setTipologiaRisposta(rispostaInvitoDto.getRisposta()));
			inviti.forEach(i -> invitoService.salvaInvito(i));
			return new ResponseEntity<>(new Messaggio("L'invito e' stato " + rispostaInvitoDto.getRisposta().toString().toLowerCase()),
					HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<>(new Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
