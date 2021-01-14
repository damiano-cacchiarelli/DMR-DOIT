package it.unicam.dmr.doit.controller.iscritto;

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
import it.unicam.dmr.doit.service.iscritto.InvitoService;
import javassist.NotFoundException;

/**
 * Questo controller ha la responsabilita' di:
 * <ul>
 * <li>Ottenere una lista di tutti gli inviti relativi ad un Iscritto;</li>
 * <li>Ottenere uno specifico invito;</li>
 * <li>Inviare un invito;</li>
 * <li>Gestire un Invito;</li>
 * <li>Eliminare un Invito;</li>
 * </ul>
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@RestController
@RequestMapping("/invito")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerInvito {

	@Autowired
	private InvitoService invitoService;

	@GetMapping("/all")
	public ResponseEntity<?> listaInviti(Authentication authentication) {
		return new ResponseEntity<>(invitoService.listaInviti(authentication.getName()), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getInvito(@PathVariable("id") String id, Authentication authentication) {
		try {
			return Utils.creaRisposta(invitoService.getInvito(id, authentication.getName()), HttpStatus.OK);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
		/*
		 * if (!invitoService.esisteInvito(id)) return new ResponseEntity<>(new
		 * Messaggio("Invito inesistente"), HttpStatus.NOT_FOUND); List<Invito>
		 * listaInviti = invitoService.listaInviti(authentication.getName());
		 * Optional<Invito> invito = listaInviti.stream().filter(i ->
		 * i.getId().equals(id)).findFirst(); if (invito.isEmpty()) return new
		 * ResponseEntity<>(new Messaggio("Invito inesistente"), HttpStatus.NOT_FOUND);
		 * return new ResponseEntity<>(invito.get(), HttpStatus.OK);
		 */
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PostMapping("/invia")
	public ResponseEntity<Messaggio> inviaInvito(@Valid @RequestBody InvitoDto invitoDto, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.invia(invitoDto, authentication.getName());
			return Utils.creaMessaggio("Invito inviato!", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
		/*
		 * if (bindingResult.hasErrors()) return
		 * Utils.creaMessaggio(Utils.getErrore(bindingResult), HttpStatus.BAD_REQUEST);
		 * for (String idDest : invitoDto.getIdDestinatario()) { if
		 * (!iscrittoService.existsByIdentificativo(idDest)) return new
		 * ResponseEntity<>(new Messaggio("Id destinatario inesistente"),
		 * HttpStatus.NOT_FOUND); } if
		 * (!progettoService.existsById(invitoDto.getIdProgetto())) return new
		 * ResponseEntity<>(new Messaggio("Progetto inesistente"),
		 * HttpStatus.NOT_FOUND);
		 * 
		 * for (String idDest : invitoDto.getIdDestinatario()) { if
		 * (authentication.getName().equals(idDest)) continue; Iscritto mittente =
		 * iscrittoService.findByIdentificativo(authentication.getName()).get();
		 * Iscritto destinatario = iscrittoService.findByIdentificativo(idDest).get();
		 * Progetto progetto =
		 * progettoService.findById(invitoDto.getIdProgetto()).get(); try {
		 * mittente.getGestoreMessaggi().inviaMessaggio(destinatario,
		 * invitoDto.getContenuto(), progetto, invitoDto.getTipologiaInvito());
		 * 
		 * } catch (IllegalArgumentException e) { return new ResponseEntity<>(new
		 * Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST); }
		 * iscrittoService.salva(mittente); iscrittoService.salva(destinatario); }
		 * return new ResponseEntity<>(new Messaggio("Invito inviato"), HttpStatus.OK);
		 */
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@DeleteMapping("/elimina")
	public ResponseEntity<Messaggio> eliminaInvito(@Valid @RequestBody EliminazioneInvitoDto eliminazioneInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.elimina(eliminazioneInvitoDto, authentication.getName());
			return Utils.creaMessaggio("Invito eliminato!", HttpStatus.OK);
		} catch (IllegalStateException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
		/*
		 * if (bindingResult.hasErrors()) return new ResponseEntity<>(new
		 * Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST); if
		 * (!invitoService.esisteInvito(eliminazioneInvitoDto.getIdInvito())) return new
		 * ResponseEntity<>(new Messaggio("Id invito inesistente"),
		 * HttpStatus.NOT_FOUND);
		 * 
		 * Invito i =
		 * invitoService.getInvito(eliminazioneInvitoDto.getIdInvito()).get(); Iscritto
		 * mittente = i.getMittente(); Iscritto destinatario = i.getDestinatario(); if
		 * (!mittente.getIdentificativo().equals(authentication.getName()) &&
		 * !destinatario.getIdentificativo().equals(authentication.getName())) { return
		 * new ResponseEntity<>(new
		 * Messaggio("Impossibile eliminare l'invito (non sei autorizzato)"),
		 * HttpStatus.BAD_REQUEST); } Iscritto iscritto =
		 * iscrittoService.findByIdentificativo(authentication.getName()).get(); try {
		 * iscritto.getGestoreMessaggi().eliminaMessaggio(eliminazioneInvitoDto.
		 * getIdInvito(), eliminazioneInvitoDto.isOpzioni()); } catch
		 * (NoSuchElementException e) { new ResponseEntity<>(new
		 * Messaggio("Invito inesistente"), HttpStatus.BAD_REQUEST); } if
		 * (mittente.getIdentificativo().equals(authentication.getName())) { if
		 * (eliminazioneInvitoDto.isOpzioni())
		 * invitoService.eliminaInvito(eliminazioneInvitoDto.getIdInvito(),
		 * List.of(RuoloSoggetto.MITTENTE, RuoloSoggetto.DESTINATARIO)); else
		 * invitoService.eliminaInvito(eliminazioneInvitoDto.getIdInvito(),
		 * List.of(RuoloSoggetto.MITTENTE)); } else
		 * invitoService.eliminaInvito(eliminazioneInvitoDto.getIdInvito(),
		 * List.of(RuoloSoggetto.DESTINATARIO)); return new ResponseEntity<>(new
		 * Messaggio("Invito eliminato"), HttpStatus.OK);
		 */
	}

	@PreAuthorize("hasRole('PROPONENTE') or hasRole('PROGETTISTA') or hasRole('ESPERTO')")
	@PutMapping("/gestisci")
	public ResponseEntity<Messaggio> gestisciInvito(@Valid @RequestBody RispostaInvitoDto rispostaInvitoDto,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors())
			return Utils.creaMessaggioDaErrore(bindingResult);

		try {
			invitoService.gestisci(rispostaInvitoDto, authentication.getName());
			return Utils.creaMessaggio("L'invito e' stato " + rispostaInvitoDto.getRisposta().toString().toLowerCase(),
					HttpStatus.OK);
		} catch (IllegalStateException | IllegalArgumentException e) {
			return Utils.creaMessaggio(e, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return Utils.creaMessaggio(e, HttpStatus.NOT_FOUND);
		}
		/*
		 * if (bindingResult.hasErrors()) return new ResponseEntity<>(new
		 * Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST); if
		 * (!invitoService.esisteInvito(rispostaInvitoDto.getIdInvito())) return new
		 * ResponseEntity<>(new Messaggio("Id invito inesistente"),
		 * HttpStatus.NOT_FOUND); Invito invito =
		 * invitoService.getInvito(rispostaInvitoDto.getIdInvito()).get(); if
		 * (!invito.getIdDestinatario().equals(authentication.getName())) return new
		 * ResponseEntity<>( new
		 * Messaggio("Non sei autorizzato a rispondere a questo invito (non sei il destinatario)"
		 * ), HttpStatus.BAD_REQUEST);
		 * 
		 * List<Invito> inviti =
		 * invitoService.getInviti(rispostaInvitoDto.getIdInvito()).stream().map(
		 * Optional::get) .collect(Collectors.toList()); try { inviti.forEach(i ->
		 * i.setTipologiaRisposta(rispostaInvitoDto.getRisposta())); inviti.forEach(i ->
		 * invitoService.salvaInvito(i));
		 * 
		 * Progetto p = progettoService.findById(invito.getIdProgetto()).get();
		 * if(invito.getTipologiaInvito() == TipologiaInvito.PROPOSTA ||
		 * invito.getTipologiaInvito() == TipologiaInvito.RICHIESTA) {
		 * if(invito.getTipologiaRisposta() == TipologiaRisposta.ACCETTATA) {
		 * 
		 * Progettista pr = (Progettista)
		 * iscrittoService.getRuolo(authentication.getName(),
		 * TipologiaRuolo.ROLE_PROGETTISTA).get();
		 * p.getGestoreCandidati().aggiungiCandidato(pr); } }
		 * if(invito.getTipologiaInvito() == TipologiaInvito.PROPOSTA)
		 * p.getGestoreCandidati().confermaCandidato(authentication.getName());
		 * progettoService.salvaProgetto(p);
		 * 
		 * return new ResponseEntity<>( new Messaggio("L'invito e' stato " +
		 * rispostaInvitoDto.getRisposta().toString().toLowerCase()), HttpStatus.OK); }
		 * catch (Exception e) { return new ResponseEntity<>(new
		 * Messaggio(e.getMessage()), HttpStatus.BAD_REQUEST); }
		 */
	}
}
