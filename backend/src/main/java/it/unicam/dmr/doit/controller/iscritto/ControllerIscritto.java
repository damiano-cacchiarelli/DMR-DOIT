package it.unicam.dmr.doit.controller.iscritto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.IscrittoDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

/**
 * Responsabilità: 
 * - visualizza messaggi
 * - aggiungi ruolo
 * - disconnettiti
 */
@RestController
@RequestMapping("/iscritto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerIscritto<I extends Iscritto, R extends IscrittoRepository<I>, S extends IscrittoService<I, R>> {
	
	@Autowired
	protected S iscrittoService;
	
	@PutMapping("/aggiungi_ruolo/{id}")
	public ResponseEntity<?> aggiungiRuolo(@PathVariable("id") String identificativo, @RequestBody RuoloDto ruolo) {
		if(!iscrittoService.existsById(identificativo))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);
		I iscritto = iscrittoService.findByIdentificativo(identificativo).get();
		List<TipologiaRuolo> ruoliDisponibili = new ArrayList<>(iscritto.getTipoRuoliPossibili());
		ruoliDisponibili.removeAll(iscritto.getTipologiaRuoli());
		if(ruoliDisponibili.contains(ruolo.getRuolo())) {
			Ruolo r = Ruolo.create(ruolo.getRuolo());
			iscritto.addRuolo(r);
			iscrittoService.save(iscritto);
			return new ResponseEntity<>(new Messaggio("Il ruolo è stato aggiunto!"), HttpStatus.OK);
		}
		else return new ResponseEntity<>(new Messaggio("Il ruolo non è disponibile."), HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/elimina/{id}")
	public ResponseEntity<?> elimina(@PathVariable("id") String identificativo){
		if(!iscrittoService.existsById(identificativo))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);	
		iscrittoService.delete(identificativo);
		return new ResponseEntity<>(new Messaggio("L'iscritto è stato eliminato"), HttpStatus.OK); 
	}
	
	protected ResponseEntity<?> canUpdate(String identificativo, IscrittoDto iscrittoDto){
		if(!iscrittoService.existsById(identificativo))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);
		if(StringUtils.isBlank(iscrittoDto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo dell'iscritto è obbligatorio"), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(new Messaggio("L'iscritto è stato aggiornato"), HttpStatus.OK); 
	}
}