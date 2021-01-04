package it.unicam.dmr.doit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import it.unicam.dmr.doit.dataTransferObject.IscrittoDto;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.security.jwt.JwtProvider;
import it.unicam.dmr.doit.service.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@RestController
@RequestMapping("/public/iscritto")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerIscritto<I extends Iscritto, R extends IscrittoRepository<I>, S extends IscrittoService<I, R>> {
	@Autowired
	protected S iscrittoService;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;

	//TODO: da aggiungere sopra i metodi: @PreAuthorize("hasRole('PROPONENTE')")
	
	@PostMapping("/accedi")
	public ResponseEntity<?> login(@Valid @RequestBody LoginIscritto loginIscritto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio("Campi non validi"), HttpStatus.BAD_REQUEST);
		if(!iscrittoService.existsById(loginIscritto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo non esiste!"), HttpStatus.NOT_FOUND);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginIscritto.getIdentificativo(), loginIscritto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
	
	@GetMapping("/lista")
	public List<I> getAllIscritti() {
		return iscrittoService.getAll();
	}

	@GetMapping("/{identificativo}")
	public ResponseEntity<?> getIscritto(@PathVariable("identificativo") String identificativo) {
		if(!iscrittoService.existsById(identificativo))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);
		I iscritto = iscrittoService.findByIdentificativo(identificativo).get();
		return new ResponseEntity<>(iscritto, HttpStatus.OK);
	}

	protected ResponseEntity<?> canCreate(IscrittoDto iscrittoDto, BindingResult bindingResult){
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio("Campi o email non validi"), HttpStatus.BAD_REQUEST);
		if(StringUtils.isBlank(iscrittoDto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo è obbligatorio"), HttpStatus.BAD_REQUEST);
		if(iscrittoService.existsById(iscrittoDto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo esiste già"), HttpStatus.BAD_REQUEST); 
		
		return new ResponseEntity<>(new Messaggio("Iscrizione avvenuta con successo"), HttpStatus.CREATED); 
	}
	
	//@PutMapping("/aggiorna/{id}")
	protected ResponseEntity<?> canUpdate(String identificativo, IscrittoDto iscrittoDto){
		if(!iscrittoService.existsById(identificativo))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);
		if(StringUtils.isBlank(iscrittoDto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo dell'iscritto è obbligatorio"), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(new Messaggio("L'iscritto è stato aggiornato"), HttpStatus.OK); 
	}
	
	@PutMapping("/{identificativo}/aggiungi_ruolo")
	public ResponseEntity<?> aggiungiRuolo(@PathVariable("identificativo") String identificativo, @RequestBody RuoloDto ruolo) {
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
	
	@DeleteMapping("/elimina/{identificativo}")
	public ResponseEntity<?> elimina(@PathVariable("identificativo") String identificativo){
		if(!iscrittoService.existsById(identificativo))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);	
		iscrittoService.delete(identificativo);
		return new ResponseEntity<>(new Messaggio("L'iscritto è stato eliminato"), HttpStatus.OK); 
	}
}