package it.unicam.dmr.doit.controller.iscritto;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unicam.dmr.doit.controller.Utils;
import it.unicam.dmr.doit.dataTransferObject.Messaggio;
import it.unicam.dmr.doit.dataTransferObject.iscritto.IscrittoDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.security.jwt.JwtProvider;
import it.unicam.dmr.doit.service.iscritto.IscrittoService;
import it.unicam.dmr.doit.utenti.Iscritto;

/**
 * Responsabilità:
 * - autenticazione
 * - registrazione
 */
@RestController
@RequestMapping("/visitatore")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerVisitatore<I extends Iscritto, R extends IscrittoRepository<I>, S extends IscrittoService<I, R>> {

	@Autowired
	protected S iscrittoService;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getIscritto(@PathVariable("id") String idIscritto) {
		if(!iscrittoService.existsByIdentificativo(idIscritto))
			return new ResponseEntity<>(new Messaggio("L'iscritto non esiste"), HttpStatus.NOT_FOUND);
		I iscritto = iscrittoService.findByIdentificativo(idIscritto).get();
		return new ResponseEntity<>(iscritto, HttpStatus.OK);
	}
	
	@PostMapping("/accedi")
	public ResponseEntity<?> accedi(@Valid @RequestBody LoginIscritto loginIscritto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		if(!iscrittoService.existsByIdentificativo(loginIscritto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo non esiste!"), HttpStatus.NOT_FOUND);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginIscritto.getIdentificativo(), loginIscritto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}
	
	protected ResponseEntity<?> canCreate(IscrittoDto iscrittoDto, BindingResult bindingResult){
		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new Messaggio(Utils.getErrore(bindingResult)), HttpStatus.BAD_REQUEST);
		if(iscrittoService.existsByIdentificativo(iscrittoDto.getIdentificativo()))
			return new ResponseEntity<>(new Messaggio("L'identificativo esiste gia'"), HttpStatus.BAD_REQUEST); 
		
		return new ResponseEntity<>(new Messaggio("Iscrizione avvenuta con successo"), HttpStatus.CREATED); 
	}
}
