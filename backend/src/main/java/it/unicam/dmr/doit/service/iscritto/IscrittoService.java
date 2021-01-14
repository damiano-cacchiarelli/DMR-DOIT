package it.unicam.dmr.doit.service.iscritto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.repository.IscrittoRepository;
import it.unicam.dmr.doit.security.jwt.JwtProvider;
import it.unicam.dmr.doit.utenti.Iscritto;
import it.unicam.dmr.doit.utenti.ruoli.Ruolo;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;
import javassist.NotFoundException;

/**
 * Questa classe inietta ({@code @Autowired}) {@code R} ed ha la responsabilita'
 * di effettuare le operazioni di modifica, inserimento, ricerca ed eliminazione
 * di un {@code I}.
 * 
 * @author Damiano Cacchiarelli
 * @author Matteo Romagnoli
 * @author Roberto Cesetti
 */
@Service
@Transactional
public class IscrittoService<I extends Iscritto, R extends IscrittoRepository<I>> {

	@Autowired
	protected R iscrittoRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;
	
	// SEZIONE METODI CONTROLLER VISITATORE - PUBBLICI
	
	public I getIscritto(String identificativo) throws NotFoundException{
		if (!iscrittoRepository.existsById(identificativo))
			throw new NotFoundException("L'iscritto non esiste");
		return iscrittoRepository.findById(identificativo).get();
	}	
	
	public ResponseEntity<Ruolo> getIscritto(String identificativo, TipologiaRuolo ruolo) throws NoSuchElementException {
		return new ResponseEntity<>(getRuolo(identificativo, ruolo).get(), HttpStatus.OK);
	}
	
	public JwtDto accedi(LoginIscritto loginIscritto) throws NotFoundException {
		if (!iscrittoRepository.existsById(loginIscritto.getIdentificativo()))
			throw new NotFoundException("L'iscritto non esiste");
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginIscritto.getIdentificativo(), loginIscritto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		return new JwtDto(jwt);
	}	
	
	// SEZIONE METODI CONTROLLER ISCRITTO - PRIVATI
	
	public boolean aggiungiRuolo(String identificativo, @Valid RuoloDto ruolo) {
		I iscritto = iscrittoRepository.findById(identificativo).get();
		if(getRuoliDisponibili(identificativo).contains(ruolo.getRuolo())) {
			Ruolo r = Ruolo.create(ruolo.getRuolo());
			iscritto.addRuolo(r);
			iscrittoRepository.save(iscritto);
			return true;
		}
		return false;
	}
	
	public List<TipologiaRuolo> getRuoliDisponibili(String identificativo) {
		if (!existsByIdentificativo(identificativo))
			throw new NoSuchElementException("Identificativo inesistente.");
		I iscritto = findByIdentificativo(identificativo).get();
		List<TipologiaRuolo> ruoliDisponibili = new ArrayList<>(iscritto.getTipoRuoliPossibili());
		ruoliDisponibili.removeAll(iscritto.getTipologiaRuoli());
		return ruoliDisponibili;
	}
	
	public void elimina(String identificativo) {
		iscrittoRepository.deleteById(identificativo);
	}
	
	// PROBABILMENTE DA RIMUOVERE
	
	public List<I> getAll() {
		return iscrittoRepository.findAll();
	}

	public Optional<I> findByIdentificativo(String identificativo) {
		return iscrittoRepository.findById(identificativo);
	}

	public boolean existsByIdentificativo(String identificativo) {
		return iscrittoRepository.existsById(identificativo);
	}

	public void salva(I iscritto) {
		iscrittoRepository.save(iscritto);
	}

	public Optional<Ruolo> getRuolo(String identificativo, TipologiaRuolo ruolo) throws NoSuchElementException {
		if (!existsByIdentificativo(identificativo))
			throw new NoSuchElementException("Identificativo inesistente.");
		I iscritto = findByIdentificativo(identificativo).get();
		return iscritto.getRuoli().stream().filter(r -> r.getRuolo().equals(ruolo)).findFirst();
	}

	public List<I> getEspertiConsigliati(List<TagDto> tags) {
		return iscrittoRepository.findEsperti();
	}
}
