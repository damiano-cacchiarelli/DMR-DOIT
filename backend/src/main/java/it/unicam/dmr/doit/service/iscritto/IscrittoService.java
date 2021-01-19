package it.unicam.dmr.doit.service.iscritto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	public I findById(String identificativo) throws NotFoundException {
		return iscrittoRepository.findById(identificativo)
				.orElseThrow(() -> new NotFoundException("L'iscritto non esiste"));
	}

	public JwtDto accedi(LoginIscritto loginIscritto) throws NotFoundException, AuthenticationException {
		if (!iscrittoRepository.existsById(loginIscritto.getIdentificativo()))
			throw new NotFoundException("L'iscritto non esiste");
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginIscritto.getIdentificativo(), loginIscritto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generaToken(authentication);
			return new JwtDto(jwt);
		} catch (Exception e) {
			throw new AuthenticationException("Identificativo o password errati.");
		}
	}

	public Object getByRuolo(TipologiaRuolo roleProgettista, String idIscritto)
			throws NoSuchElementException, NotFoundException {
		return findById(idIscritto).getRuolo(roleProgettista);
	}

	// SEZIONE METODI CONTROLLER ISCRITTO - PRIVATI

	public boolean aggiungiRuolo(String identificativo, @Valid RuoloDto ruolo) throws NotFoundException {
		I iscritto = iscrittoRepository.findById(identificativo).get();
		if (getRuoliDisponibili(identificativo).contains(ruolo.getRuolo())) {
			Ruolo r = Ruolo.create(ruolo.getRuolo());
			iscritto.addRuolo(r);
			iscrittoRepository.save(iscritto);
			return true;
		}
		return false;
	}

	public List<TipologiaRuolo> getRuoliDisponibili(String identificativo) throws NotFoundException {
		I iscritto = iscrittoRepository.findById(identificativo)
				.orElseThrow(() -> new NotFoundException("Identificativo inesistente."));
		List<TipologiaRuolo> ruoliDisponibili = new ArrayList<>(iscritto.getTipoRuoliPossibili());
		ruoliDisponibili.removeAll(iscritto.getTipologiaRuoli());
		return ruoliDisponibili;
	}

	public boolean esisteIscrittoByRuolo(String identificativo, TipologiaRuolo ruolo) {
		I iscritto = iscrittoRepository.findById(identificativo).orElse(null);
		if (iscritto != null)
			if (iscritto.getRuoli().stream().filter(r -> r.getRuolo() == ruolo).findFirst().orElse(null) != null)
				return true;
		return false;
	}

	public void elimina(String identificativo) {
		iscrittoRepository.deleteById(identificativo);
	}

	public List<I> getEspertiConsigliati(List<TagDto> tags) {
		return iscrittoRepository.findEsperti();
	}
}
